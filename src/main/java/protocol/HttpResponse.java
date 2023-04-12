package protocol;

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
    private final String httpVersion = "HTTP/1.1";
    private StatusCode statusCode;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.body = new byte[0];
        this.headers = new HashMap<>();
    }

    public HttpResponse forward(StatusCode statusCode, String path) throws IOException {
        try {
            ContentType type = ContentType.of(path);

            this.statusCode = statusCode;
            this.body = Files.readAllBytes(new File(type.getTypeDirectory() + path).toPath());

            setHeader("Content-Type", type.getHeadValue());
            setHeader("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            logger.error(e.getMessage());
            forward(StatusCode.NOT_FOUND, "/error/404.html");
        }
        return this;
    }

    /**
     * redirect responseLine을 준비하고, header에 Location을 매개변수로 받은 경로로 추가한다.
     *
     * @param path
     * @return
     */
    public HttpResponse redirect(String path) {
        statusCode = StatusCode.FOUND;
        setHeader("Location", path);
        return this;
    }

    /**
     * 모든 멤버가 준비가 된 후, 마지막에 호출하여 DataOutputStream으로 작성한 후 flush 한다.
     */
    public void response() {
        try {
            writeStatusCode();
            writeHeader();

            if (body.length > 0) {
                writeBody();
            }

            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * header에 키와 값을 추가한다. 외부에서 수동으로 넣을 수도 있을까 싶어 public으로 공개해두었다.
     *
     * @param key
     * @param value
     */
    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * responseLine에 저장된 값을 DataOutputStream에 작성한다.
     *
     * @throws IOException
     */
    private void writeStatusCode() throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", httpVersion, statusCode.getResponseLine()));
    }

    /**
     * header에 저장된 키와 값을 DataOutputStream에 작성한다.
     *
     * @throws IOException
     */
    private void writeHeader() throws IOException {
        for (Map.Entry<String, String> entries : headers.entrySet()) {
            dos.writeBytes(String.format("%s: %s\r\n", entries.getKey(), entries.getValue()));
        }

        dos.writeBytes("\r\n");
    }

    /**
     * body에 저장된 값을 DataOutputStream에 작성한다.
     */
    private void writeBody() {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
