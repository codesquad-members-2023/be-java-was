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

    private final DataOutputStream DOS;
    private final String HTTP_VERSION;
    private StatusCode statusCode;
    private final Map<String, String> HEADERS;
    private byte[] body;

    public HttpResponse(String version, DataOutputStream dos) {
        this.DOS = dos;
        this.HTTP_VERSION = version;
        this.body = new byte[0];
        this.headers = new HashMap<>();
    }

    /**
     * forward responseLine, 반환할 body를 준비한다. 단, static에서 파일을 찾아 반환한다.
     * @param path
     * @return
     * @throws IOException
     */
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

    /**
     * forward responseLine, 반환할 body를 준비한다.
     * @param path
     * @return
     * @throws IOException
     */
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

    /**
     * redirect responseLine을 준비하고, header에 Location을 매개변수로 받은 경로로 추가한다.
     * @param path
     * @return
     */
    public HttpResponse redirect(String path) {
        responseLine = "HTTP/1.1 302 Found";
        setHeader("Location", path);
        return this;
    }

    /**
     * 모든 멤버가 준비가 된 후, 마지막에 호출하여 DataOutputStream으로 작성한 후 flush 한다.
     */
    public void response() {
        try {
            writeStatusCode();    // status 코드에 해당되는 ResponseLine을 작성한다.
            writeHeader();  // 응답하기 전, head를 작성한다..

            if (body.length > 0) {  // body가 있을 경우
                writeBody();
            }

            DOS.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * header에 키와 값을 추가한다. 외부에서 수동으로 넣을 수도 있을까 싶어 public으로 공개해두었다.
     * @param key
     * @param value
     */
    public HttpResponse setHeader(String key, String value) {
        HEADERS.put(key, value);
        return this;
    }

    /**
     * responseLine에 저장된 값을 DataOutputStream에 작성한다.
     * @throws IOException
     */
    private void writeStatusCode() throws IOException {
        DOS.writeBytes(String.format("%s %s \r\n", HTTP_VERSION, statusCode.getResponseLine()));
    }

    /**
     * header에 저장된 키와 값을 DataOutputStream에 작성한다.
     * @throws IOException
     */
    private void writeHeader() throws IOException {
        for (Map.Entry<String, String> entries : HEADERS.entrySet()) {
            DOS.writeBytes(String.format("%s: %s\r\n", entries.getKey(), entries.getValue()));
        }

        DOS.writeBytes("\r\n");
    }

    /**
     * body에 저장된 값을 DataOutputStream에 작성한다.
     */
    private void writeBody() {
        try {
            DOS.write(body, 0, body.length);
            DOS.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
