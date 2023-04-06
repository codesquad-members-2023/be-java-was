package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.WebServer;

public class WebClient {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private final static String serverName = "localhost";

    /**
     * 클라이언트 연결 및 index.html HTTP GET 요청 전송
     * @param logger
     */

    public static void connect(Logger logger) {
        try {
            logger.debug("서버 연결 : {} 포트 번호 : {}", serverName, DEFAULT_PORT);
            Socket socket = new Socket(serverName, DEFAULT_PORT);

            BufferedReader readDataFromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            DataOutputStream sendDataToServer = new DataOutputStream(socket.getOutputStream());

            //Server로 HTTP 요청 전송
            sendDataToServer.writeBytes("GET / HTTP/1.1\r\n"
                    + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
                    + "Accept-Encoding: gzip, deflate, br\r\n"
                    + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n"
                    + "Cache-Control: no-cache\r\n\r\n");

            //첫 줄 읽기
            String httpResponseLine = readDataFromServer.readLine();
            logger.debug("responseLine : {}", httpResponseLine);

            //Header 읽기
            String httpResponseHeaders;
            while (!(httpResponseHeaders = readDataFromServer.readLine()).equals("")) {
                logger.debug("header : {}", httpResponseHeaders);
            }
            readDataFromServer.readLine();

            //Body 읽기
            String httpResponseBody;
            while ((httpResponseBody = readDataFromServer.readLine()) != null) {
                logger.debug("Body : {}", httpResponseBody);
            }

            //소켓 종료 안함
            while (true) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        connect(logger);
    }
}
