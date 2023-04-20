package response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import model.User;
import templateEngine.PoroTouch;
import view.ModelAndView;

/**
 * 이 클래스는 Response에 대한 메타 데이터를 저장합니다.
 */
public class HttpResponse {

    String httpVersion;
    Status status;
    HttpHeaders httpHeaders;
    ModelAndView modelAndView;

    public HttpResponse() {
        httpHeaders = new HttpHeaders();
        modelAndView = new ModelAndView();
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

    public HttpResponse setViewName(String viewName) {
        modelAndView.setViewName(viewName);
        return this;
    }

    public HttpResponse setContentsType(ContentsType contentsType) {
        modelAndView.setContentsType(contentsType);
        return this;
    }

    public void setModelAttribute(String attributeName, Object attribute) {
        modelAndView.setModelAttribute(attributeName, attribute);
    }

    public void setContentLength(int contentLength) {
        httpHeaders.put("Content-Length", String.valueOf(contentLength));
    }

    public byte[] getResponseLine() {
        final String CRLF = "\r\n";

        String headLine = String.join(" ", httpVersion, status.getStatusCode(),
            status.getStatusMessage());
        return (headLine + CRLF + httpHeaders).getBytes();
    }

    public byte[] render() throws IOException {
        byte[] headers = getResponseLine();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Body가 있는 경우
        outputStream.write(headers);
        if (modelAndView.hasBody()) {
            //템플릿 엔진 기능 추가
            byte[] body = Files.readAllBytes(modelAndView.getFile().toPath());
            if (modelAndView.isDynamicFile()) {
                try {
                    body = PoroTouch.render(body, modelAndView).getBytes(StandardCharsets.UTF_8);
                } catch (InvocationTargetException | IllegalAccessException e){
                    e.printStackTrace();
                }
            }

            //ContentLength도 같이 바꿔줘야함
            setContentLength(body.length);
            outputStream.write(body);
        }

        return outputStream.toByteArray();
    }
}
