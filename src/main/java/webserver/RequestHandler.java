package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.RequestHeader;
import model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // 리퀘스트 헤더 전체 리스트에 삽입
            List<String> entireRequest = new ArrayList<>();
            String line;
            while (!(line = br.readLine()).equals("")) {
                entireRequest.add(line);
            }

            // 리퀘스트 라인 생성
            String[] requestLineSplit = entireRequest.get(0).split(" ");
            RequestLine requestLine = new RequestLine(requestLineSplit[0], requestLineSplit[1], requestLineSplit[2]);

            // 리퀘스트 전문 로거로 출력
            StringBuilder requestBuilder = new StringBuilder();
            for (String str : entireRequest) {
                requestBuilder.append(str);
            }
            RequestHeader requestHeader = new RequestHeader(requestBuilder.toString());
            logger.info("RequestHeader : " + requestHeader.getHeaderMessage());

            // GetURLCollection 메서드를 URL로 변환후 getMappingURLMap에 삽입
            Map<String, Method> getMappingURLMap = new HashMap<>();
            GetURLCollection getUrlCollection = new GetURLCollection();

            Class<?> loadClass = getUrlCollection.getClass();
            Method[] methods = loadClass.getMethods();

            // 메서드명이 mapping이라는 문자열로 시작하면 getMappingURLMap에 삽입
            for (Method m : methods) {
                if (m.getName().startsWith("mapping")) {
                    getMappingURLMap.put(getUrlCollection.methodNameToURL(m.getName()), m);
                }
            }

            String result = "";
            DataOutputStream dos = new DataOutputStream(out);
            logger.info("mappingKeySet : " + getMappingURLMap.keySet().toString());
            logger.info("getURL : " + requestLine.getURL());
            // requestLine에 URL이 getMappingURLMap에 있다면
            if(getMappingURLMap.containsKey(requestLine.getURL())) {
                // 리플렉션을 사용하여 메서드 반환값으로 해당 파일을 찾아 response200Header로 응답
                Method method = getMappingURLMap.get(requestLine.getURL());
                result = (String) method.invoke(getUrlCollection);
                logger.info("result : " + result);
                byte[] body = Files.readAllBytes(new File(result + requestLine.getURLPath()).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else {
                // 메서드가 없으므로 404 응답
                response404Header(dos);
            }

        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("200" + e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: 0\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("404" + e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error("body" + e.getMessage());
        }
    }
}
