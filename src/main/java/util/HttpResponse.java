package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private Logger log = LoggerFactory.getLogger(getClass());

    public void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String findController(String path) {
        String[] split = path.split("/");
        return split[1];
    }

    public String getContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".js")) {
            return "application/javascript";
        }

        return "text/html";
    }

    public byte[] findBody(String contentType, String path) throws IOException {
        if (contentType.equals("text/css")) {
            return getClass().getResourceAsStream("/static" + path).readAllBytes();
        }
        if (contentType.equals("application/javascript")) {
            return getClass().getResourceAsStream("/static" + path).readAllBytes();
        }

        return getClass().getResourceAsStream("/templates" + path).readAllBytes();
    }

    public void processResponse(String path, HttpResponse response, OutputStream out) throws IOException {
        String contentType = response.getContentType(path);
        byte[] body = findBody(contentType, path);
        DataOutputStream dos = new DataOutputStream(out);
        response200Header(dos, body.length, contentType);
        responseBody(dos, body);
    }
}
