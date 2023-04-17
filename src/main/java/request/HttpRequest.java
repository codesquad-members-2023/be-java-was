package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;
import util.SingletonContainer;
import webserver.ContentTypeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private HttpRequestLine httpRequestLine;
    private Map<String, String> httpRequestHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        this.httpRequestHeader = new HashMap<>();
        this.httpRequestLine = new HttpRequestLine(br.readLine());

        String requestHeaders = br.readLine();

        while (!requestHeaders.equals("")) {
            String separator = ":";
            int separatorIdx = requestHeaders.indexOf(separator);
            String headersName = requestHeaders.substring(0, separatorIdx).trim();
            String headersValue = requestHeaders.substring(separatorIdx + 1).trim();

            logger.info("headers name [{}], value [{}]", headersName, headersValue);
            saveHeaderNameAndValue(headersName, headersValue);
            requestHeaders = br.readLine();
        }

        ContentTypeParser contentTypeParser = new ContentTypeParser();
        String returnType = contentTypeParser.getPriorityContentType(httpRequestHeader.get("Accept"), getExtension());
        logger.info("return Type = {}", returnType);
    }

    private void saveHeaderNameAndValue(String name, String value) {
        httpRequestHeader.put(name, value);
    }

    public String getValueByNameInHeader(String name) {
        return httpRequestHeader.get(name);
    }

    public String getExtension() {
        String fileName = httpRequestLine.getValueByNameInRequestLine("returnUrl");
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    public String getValueByNameInRequestLine(String name) {
        return httpRequestLine.getValueByNameInRequestLine(name);
    }
}
