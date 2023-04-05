package webserver.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse {
    private DataOutputStream dos;
    private String responseLine;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public HttpResponse forward(String path) throws IOException {
        body = getClass().getResourceAsStream("/templates" + path).readAllBytes();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html; charset=UTF-8 \r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public HttpResponse redirect(String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: "+path+"\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

        return this;
    }

    private HttpResponse setBody() {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }


    public void response() {
        try {
            if (body.length > 0) setBody(); // 응답 하기 전, body가 있는 경우 자동으로 body를 뒤에 붙인다.

            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
