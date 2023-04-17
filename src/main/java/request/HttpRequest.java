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

    private Map<String, String> httpRequestLineMap;
    private Map<String, String> httpRequestHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        this.httpRequestLineMap = new HashMap<>();
        this.httpRequestHeader = new HashMap<>();

        String requestLine = br.readLine();

        if (requestLine != null) {
            String[] parsedUrl = RequestParser.separateUrls(requestLine);
            String httpMethod = parsedUrl[0];
            String resourceUrl = parsedUrl[1];

            String returnUrl = resourceUrl.split("\\?")[0];
            if (returnUrl.equals("/")) {
                returnUrl = "/index.html";
            }

            if (resourceUrl.startsWith("/user")) {
                returnUrl = SingletonContainer.getUserController().mapToFunctions(httpMethod, resourceUrl);
            }

            httpRequestLineMap.put("httpMethod", httpMethod);
            httpRequestLineMap.put("resourceUrl", resourceUrl);
            httpRequestLineMap.put("returnUrl", returnUrl);
        }

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

    public String getValueByNameInRequestLine(String name) {
        return httpRequestLineMap.get(name);
    }

    public String getExtension() {
        String fileName = httpRequestLineMap.get("returnUrl");
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
}
