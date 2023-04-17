package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import webserver.ContentTypeParser;

import java.io.DataOutputStream;
import java.io.IOException;

public class RightResponseBuilder implements HttpResponseBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());
    HttpRequest httpRequest;
    ContentTypeParser contentTypeParser = new ContentTypeParser();

    public RightResponseBuilder(HttpRequest httpRequest) throws IOException {
        this.httpRequest = httpRequest;
    }

    @Override
    public void buildResponse(DataOutputStream dos, String extension, byte[] body) {
        try {
            String contentType = contentTypeParser.getPriorityContentType(httpRequest.getValueByNameInHeader("Accept"), extension);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
