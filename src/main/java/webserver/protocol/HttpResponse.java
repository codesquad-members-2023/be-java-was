package webserver.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private String responseLine;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.body = new byte[0];
        this.headers = new HashMap<>();
    }

    public HttpResponse forwardStatic(String path) throws IOException {
        try {
            responseLine = "HTTP/1.1 200 OK";
            body = Files.readAllBytes(new File("src/main/resources/static"+path).toPath());

            setHeader("Content-Type", ContentType.of(path).value);
            setHeader("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public HttpResponse forward(String path) throws IOException {
        try {
            responseLine = "HTTP/1.1 200 OK";
            body = Files.readAllBytes(new File("src/main/resources/templates"+path).toPath());

            setHeader("Content-Type", ContentType.of(path).value);
            setHeader("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public HttpResponse redirect(String path) {
        responseLine = "HTTP/1.1 302 Found";
        setHeader("Location", path);
        return this;
    }

    public void response() {
        try {
            writeStatusCode();    // status 코드에 해당되는 ResponseLine을 작성한다.
            writeHeader();  // 응답하기 전, head를 넣는다.

            if (body.length > 0) {
                writeBody();
            }

            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    private void writeStatusCode() throws IOException {
        dos.writeBytes(responseLine + " \r\n");
    }

    private void writeHeader() throws IOException {
        for (Map.Entry<String, String> entries : headers.entrySet()) {
            dos.writeBytes(String.format("%s: %s\r\n", entries.getKey(), entries.getValue()));
        }
    }

    private void writeBody() {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
