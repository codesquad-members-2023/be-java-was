package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final DataOutputStream dataOutputStream;
    private final int lengthOfBodyContent;
    private final String accept;

    public Response(DataOutputStream dataOutputStream, int lengthOfBodyContent, String accept) {
        this.dataOutputStream = dataOutputStream;
        this.lengthOfBodyContent = lengthOfBodyContent;
        this.accept = accept;
    }

    public void send200Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + accept + "; charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
