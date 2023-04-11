package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ContentTypeMapper;
import util.RequestParser;
import util.SingletonContainer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> httpRequestHeader;

    public HttpRequestHeader(BufferedReader br) throws IOException {
        this.httpRequestHeader = new HashMap<>();

        String requestLine = br.readLine();

        logger.debug("request first line = {}", requestLine);

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

            saveHeaderNameAndValue("httpMethod", httpMethod);
            saveHeaderNameAndValue("resourceUrl", resourceUrl);
            saveHeaderNameAndValue("returnUrl", returnUrl);
        }

        String requestHeaders = br.readLine();

        while (!requestHeaders.equals("")) {
            requestHeaders = br.readLine();
            String[] nameAndValue = requestHeaders.split(": ");
            if (nameAndValue.length == 2) {
                logger.info("headers name [{}], value [{}]", nameAndValue[0], nameAndValue[1]);
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

    public String getExtension() {
        String fileName = httpRequestHeader.get("returnUrl");
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
}
