package response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import view.ModelAndView;

/**
 * 이 클래스는 Response에 대한 메타 데이터를 저장합니다.
 * Body는 메모리 용량에 부담이 될 것 같아 buffer를 통해서만 전달됩니다.
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

    public HttpResponse setContentsType(ContentsType contentsType){
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
        String headLine = String.join(" ", httpVersion, status.getStatusCode(), status.getStatusMessage());
        return (headLine + "\r\n" + httpHeaders).getBytes();
    }

    public byte[] render() throws IOException {
        byte[] headers = getResponseLine();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Body가 있는 경우
        outputStream.write(headers);
        if (modelAndView.hasBody()) {
            byte[] body = Files.readAllBytes(new File(modelAndView.getPath()).toPath());

            //ContentLength도 같이 바꿔줘야함
            setContentLength(body.length);
            outputStream.write(body);
        }

        return outputStream.toByteArray();
    }


}
