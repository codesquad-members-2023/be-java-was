package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ContentTypeMapper;

import java.io.DataOutputStream;
import java.io.IOException;

public class RightResponseBuilder implements HttpResponseBuilderV1 {

    Logger logger = LoggerFactory.getLogger(getClass());
    HttpRequest httpRequest;
    ContentTypeParser contentTypeParser = new ContentTypeParser();

    public RightResponseBuilder(HttpRequest httpRequest) throws IOException {
        this.httpRequest = httpRequest;
    }

    @Override
    public void buildResponse(DataOutputStream dos, int lengthOfBodyContent, String extension) {
        try {
            String contentType = contentTypeParser.getPriorityContentType(httpRequest.getValueByName("Accept"));
            if (contentType == null) {
                contentType = ContentTypeMapper.getContentTypeByExtension(extension);
            }

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
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
