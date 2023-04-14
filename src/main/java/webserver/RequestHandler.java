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

    private static final String PATH = "src/main/resources/templates";

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
                httpRequest.addParam(readBody(br, bodyLength));
            }

            if(httpRequest.getMethod().equals("GET")) {
                httpResponse.forward(httpRequest.getUrl());
            }

            UserController userController = new UserController();

            logger.debug("getUrl = {}", httpRequest.getUrl());

            if (httpRequest.getUrl().startsWith("/user/create")) {
                String url = userController.addUser(httpRequest);
                logger.debug("create url = {}", url);
                httpResponse.redirect(url);
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String readBody(BufferedReader br, int bodyLength) throws IOException {
        char[] body = new char[bodyLength];
        br.read(body, 0, bodyLength);
        return String.valueOf(body);
    }

}
