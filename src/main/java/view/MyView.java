package view;

import servlet.HttpResponse;
import session.Cookie;
import type.ContentType;
import util.MyLittleMustache;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

public class MyView implements View{

    private String name;
    private String path;

    private final String SET_COOKIE = "Set-Cookie";

    private final String CONTENT_TYPE = "Content-Type";

    private final String CONTENT_LENGTH = "Content-Length";

    private static final int NULL_STATUS_CODE = 0;

    public MyView(){};

    public MyView(MyView myView) {
        this.name = myView.name;
        this.path = myView.path;
    }

    public MyView(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getPath() {return this.path; }

    public void render(Map<String, ?> model, HttpResponse httpResponse) throws IOException {
        byte[] body = new byte[0];
        byte[] head = new byte[0];

        if (httpResponse.getStatus() != HttpResponse.SC_FOUND) {
            File file = new File(this.path);

            resolveContentType(httpResponse, file);
            body = renderBody(model, httpResponse, file);
            resolveContentLength(httpResponse, body);
        }

        head = renderHead(httpResponse);

        byte[] result = getByteBuffer(body, head);

        DataOutputStream dataOutputStream = new DataOutputStream(httpResponse.getOutputStream());
        dataOutputStream.write(result);
        dataOutputStream.flush();
    }

    private static byte[] getByteBuffer(byte[] body, byte[] head) {
        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);
        return buffer.array();
    }


    private byte[] renderHead(HttpResponse httpResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 아래의 Reason-Phrase 는 어떻게 하는게 좋을까? ( HTTP 버전은?)
        renderStateLine(httpResponse, stringBuilder);

        // 응답 쿠키 추가
        renderCookie(httpResponse, stringBuilder);

        // 응답 헤더 추가
        renderHeader(httpResponse, stringBuilder);

        stringBuilder.append("\n");

        return stringBuilder.toString().getBytes();
    }
    private static void renderStateLine(HttpResponse httpResponse, StringBuilder stringBuilder) {
        int statusCode = httpResponse.getStatus();
        if (statusCode == NULL_STATUS_CODE) {
            statusCode = HttpResponse.SC_OK;
        }
        stringBuilder.append("HTTP/1.1").append(" ").append(statusCode).append(" ").append(statusCode).append("\n");
    }

    private static void renderHeader(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<String> headersName = httpResponse.getHeadersName();
        for (String name : headersName) {
            stringBuilder.append(name).append(":").append(httpResponse.getHead(name)).append("\n");
        }
    }

    private void renderCookie(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<Cookie> cookies = httpResponse.getCookies();
        for (Cookie cookie : cookies) {
            stringBuilder.append(SET_COOKIE).append(":").append(cookie).append("\n");
        }
    }

    private void resolveContentLength(HttpResponse response, byte[] body) {
        response.addHeader(CONTENT_LENGTH, Long.toString(body.length));
    }

    private void resolveContentType(HttpResponse response, File file) {
        if (response.containHeader(CONTENT_TYPE)) {
            return;
        }

        // TODO : 아래의 코드가 마음에 들지 않는다. (확장자 명을 이용한 contentType? 이럼 안되~)
        ContentType contentType = mapTypeByIndentifier(file.getName());
        response.setContentType(contentType);
    }

    private byte[] renderBody(Map<String, ?> model, HttpResponse httpResponse, File file) throws IOException {
        if (httpResponse.getContentType() != ContentType.HTML) {
            return Files.readAllBytes(file.toPath());
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            // TODO : System.lineSeparator()이 무엇인지 알아보기
            content.append(line).append(System.lineSeparator());
        }

        String template = content.toString();
        String output = MyLittleMustache.render(model, template);

        return output.getBytes();
    }


    // 포로형 고마워!
    private ContentType mapTypeByIndentifier(String viewName) {
        //Static 파일 경로에서 탐색
        for (ContentType contentType : ContentType.values()) {
            if (viewName.matches(contentType.getIdentifier())) {
                return contentType;
            }
        }
        throw new IllegalArgumentException("Match되는 Contents-Type이 존재하지 않습니다.");
    }
}
