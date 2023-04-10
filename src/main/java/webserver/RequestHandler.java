package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import config.AppConfig;
import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String requestLine = br.readLine();

            HttpRequest httpRequest = new HttpRequest(requestLine, readHeader(br));
            HttpResponse httpResponse = new HttpResponse(httpRequest.getVersion(), new DataOutputStream(out));

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private String readHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String header;
        while (!((header = br.readLine()).equals(""))) {
            sb.append(header).append("\n");
        }
        return sb.toString();
    }

}
