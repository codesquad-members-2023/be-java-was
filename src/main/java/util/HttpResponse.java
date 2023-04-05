package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private Logger log = LoggerFactory.getLogger(getClass());
    private int status;
    private Map<String, String> headers = new HashMap<>();

    public void processResponse(String path, HttpResponse response, OutputStream out) throws IOException {
        String contentType = response.getContentType(path);
        byte[] body = findBody(contentType, path);
        DataOutputStream dos = new DataOutputStream(out);
        responseHeader(dos, body.length, contentType);
        responseBody(dos, body);
    }


    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 " + status + " " + getStatusCode(status) + "\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");

            for (Map.Entry<String, String> header : headers.entrySet()) {
                dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".js")) {
            return "application/javascript";
        }
        if (path.endsWith(".ttf")) {
            return "font/ttf";
        }
        if (path.endsWith(".woff")) {
            return "font/woff";
        }
        if (path.endsWith(".ico")) {
            return "image/x-icon";
        }

        return "text/html";
    }

    private byte[] findBody(String contentType, String path) throws IOException {
        if (contentType.equals("text/css")) {
            return getClass().getResourceAsStream("/static" + path).readAllBytes();
        }
        if (contentType.equals("application/javascript")) {
            return getClass().getResourceAsStream("/static" + path).readAllBytes();
        }
        if (contentType.equals("font/woff")) {
            return getClass().getResourceAsStream("/static/fonts" + path).readAllBytes();
        }
        if (contentType.equals("font/ttf")) {
            return getClass().getResourceAsStream("/static/fonts" + path).readAllBytes();
        }
        log.info("contentType = {}", contentType);

        return getClass().getResourceAsStream("/templates" + path).readAllBytes();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    private String getStatusCode(int status) {
        if (status == 302) {
            return "Found";
        }

        return "OK";
    }

    public String getResponse() {
        return headers.getOrDefault("Location", "");
    }
}
