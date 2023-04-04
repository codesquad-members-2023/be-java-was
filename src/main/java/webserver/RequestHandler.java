package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import model.InputRequestParser;
import model.RequestLine;
import model.UrlMethodMappingGenerator;
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
            RequestLine requestLine = handleRequest(in);
            sendResponse(out, requestLine);
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestLine handleRequest(InputStream in) {
        InputRequestParser inputRequestParser = new InputRequestParser(in);
        RequestLine requestLine = inputRequestParser.getRequestLine();
        logger.info("RequestHeader : " + inputRequestParser.getRequestHeader().getHeaderMessage());
        return requestLine;
    }

    private void sendResponse(OutputStream out, RequestLine requestLine)
            throws InvocationTargetException, IllegalAccessException, IOException {
        Map<String, Method> urlMethodMapping = UrlMethodMappingGenerator.generateUrlMethodMapping();
        String resultPath;
        DataOutputStream dos = new DataOutputStream(out);
        if (urlMethodMapping.containsKey(requestLine.getURL())) {
            Method method = urlMethodMapping.get(requestLine.getURL());
            GetMappingProvider getMappingProvider = new GetMappingProvider();
            resultPath = (String) method.invoke(getMappingProvider);
            byte[] body = Files.readAllBytes(new File(resultPath + requestLine.getURLPath()).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
            return;
        }
        response404Header(dos);
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
