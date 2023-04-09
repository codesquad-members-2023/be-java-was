package view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import response.ContentsType;
import response.HttpResponse;
import response.Status;

public class View {

    private String viewName;
    private ContentsType contentsType;
    private HttpResponse httpResponse;

    public View(String viewName, ContentsType contentsType, HttpResponse httpResponse) {
        this.viewName = viewName;
        this.contentsType = contentsType;
        this.httpResponse = httpResponse;
    }

    public View(String viewName, HttpResponse httpResponse) {
        this.viewName = viewName;
        this.httpResponse = httpResponse;
    }

    /**
     * httpResponse에서 header 정보를 읽어와 첫 줄과 header 메시지를 작성하고,
     * viewName으로 파일 경로에 있는 파일의 Body와 합쳐 반환한다.
     * @return httpResponseMessage
     * @throws IOException
     */
    public byte[] render() throws IOException {
        byte[] headers = writeHeaderBytes(httpResponse);
        byte[] body = Files.readAllBytes(
            new File(contentsType.getLocatedPath() + viewName).toPath());
        setContentLength(body.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(headers);
        outputStream.write(body);

        return outputStream.toByteArray();
    }

    public void setContentLength(int bodyLength) {
        httpResponse.setHttpHeaders(bodyLength);
    }

    private byte[] writeHeaderBytes(HttpResponse httpResponse) {
        return httpResponse.toString().getBytes();
    }
}
