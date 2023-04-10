package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;
import util.SingletonContainer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    Logger logger = LoggerFactory.getLogger(HttpRequestHeader.class);
    private Map<String, String> httpRequestHeader;
    private Map<String, String> contentTypeMap;

    public HttpRequestHeader(BufferedReader br) throws IOException {
        this.httpRequestHeader = new HashMap<>();
        this.contentTypeMap = new HashMap<>();
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("ico", "image/x-icon");

        String line = br.readLine();

        logger.debug("request first line = {}", line);

        if (line != null) {
            String[] parsedUrl = RequestParser.separateUrls(line);
            String httpMethod = parsedUrl[0];
            String resourceUrl = parsedUrl[1];

            String returnUrl = resourceUrl.split("\\?")[0];
            if (resourceUrl.startsWith("/user")) {
                returnUrl = SingletonContainer.getUserController().mapToFunctions(httpMethod, resourceUrl);
            }

            saveHeaderNameAndValue("httpMethod", httpMethod);
            saveHeaderNameAndValue("resourceUrl", resourceUrl);
            saveHeaderNameAndValue("returnUrl", returnUrl);
        }

        while (!line.equals("")) {
            line = br.readLine();
            String[] nameAndValue = line.split(": ");
            if (nameAndValue.length == 2) {
                saveHeaderNameAndValue(nameAndValue[0], nameAndValue[1]);
            }
        }
    }

    private void saveHeaderNameAndValue(String name, String value) {
        httpRequestHeader.put(name, value);
    }

    public String getValueByName(String name) {
        return httpRequestHeader.get(name);
    }

    private String getExtension() {
        String fileName = httpRequestHeader.get("returnUrl");
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    public void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            String contentType = contentTypeMap.get(getExtension());

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
