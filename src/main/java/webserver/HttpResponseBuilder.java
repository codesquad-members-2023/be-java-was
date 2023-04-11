package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ContentTypeMapper;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseBuilder {

    private Logger logger = LoggerFactory.getLogger(getClass());


    public void response200Header(DataOutputStream dos, int lengthOfBodyContent, String extension) {
        try {
            String contentType = ContentTypeMapper.getContentTypeByExtension(extension);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404NotFoundHeader(DataOutputStream dos, int lengthOfBodyContent, String extension) {
        try {
            String contentType = ContentTypeMapper.getContentTypeByExtension(extension);

            dos.writeBytes("HTTP/1.1 404 NotFound \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
