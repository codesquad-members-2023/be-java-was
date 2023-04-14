package webserver;

import java.io.*;
import java.net.Socket;

import Controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import util.MethodStatus;


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

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            logger.debug("line = {} ", line);
            HttpRequest httpRequest = new HttpRequest(line);

            HttpResponse httpResponse = new HttpResponse(out);


            String header = br.readLine();

            while (!header.equals("")) {
                httpRequest.addHeader(header);
                header = br.readLine();
            }

            if (httpRequest.getHeader("Content-Length") != null) { // line이 null일때 무시
                int bodyLength = Integer.parseInt(httpRequest.getHeader("Content-Length"));
                httpRequest.addParameter(readBody(br, bodyLength));
            }

            handleRequest(httpRequest, httpResponse);


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals("GET")) {
            handleGetRequest(httpRequest, httpResponse);
        } else if (httpRequest.getMethod().equals("POST")) {
            handlePostRequest(httpRequest, httpResponse);
        }
    }

    private void handleGetRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getUrl());
    }

    private void handlePostRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getUrl().startsWith("/user/create")) {
            UserController userController = new UserController();
            String url = userController.addUser(httpRequest);
            httpResponse.redirect(url);
        }
    }

    public String readBody(BufferedReader br, int bodyLength) throws IOException {
        char[] body = new char[bodyLength];
        br.read(body, 0, bodyLength);
        return String.valueOf(body);
    }
}
