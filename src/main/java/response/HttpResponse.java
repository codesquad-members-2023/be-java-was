package response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import view.View;

/**
 * 이 클래스는 Response에 대한 메타 데이터를 저장합니다.
 * Body는 메모리 용량에 부담이 될 것 같아 buffer를 통해서만 전달됩니다.
 */
public class HttpResponse {

    String httpVersion;
    Status status;
    HttpHeaders httpHeaders;
    View view;

    public HttpResponse() {
        httpHeaders = new HttpHeaders();
    }

    public static HttpResponse builder() {
        return new HttpResponse();
    }

    public HttpResponse addHeader(String key, String value) {
        this.httpHeaders.put(key, value);
        return this;
    }

    public HttpResponse setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public HttpResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    public HttpResponse setView(View view) {
        this.view = view;
        return this;
    }

    public void setContentLength(int contentLength) {
        httpHeaders.put("Content-Length", String.valueOf(contentLength));
    }

    public byte[] getResponseLine() {
        String headLine = String.join(" ", httpVersion, status.getStatusCode(), status.getStatusMessage());
        return (headLine + "\r\n" + httpHeaders).getBytes();
    }

    public byte[] render() throws IOException {
        byte[] headers = getResponseLine();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Body가 있는 경우
        outputStream.write(headers);
        if (view.hasBody()) {
            byte[] body = Files.readAllBytes(new File(view.getPath()).toPath());
            setContentLength(body.length);
            outputStream.write(body);
        }

        return outputStream.toByteArray();
    }


}
