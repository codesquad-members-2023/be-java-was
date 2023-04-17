package request;

import util.RequestParser;
import util.SingletonContainer;

import java.util.HashMap;

public class HttpRequestLine {

    private HashMap<String, String> httpRequestLineMap = new HashMap<>();

    public HttpRequestLine(String requestLine) {

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

            saveRequestLineNameAndValue("httpMethod", httpMethod);
            saveRequestLineNameAndValue("resourceUrl", resourceUrl);
            saveRequestLineNameAndValue("returnUrl", returnUrl);
        }
    }

    public String getValueByNameInRequestLine(String name) {
        return httpRequestLineMap.get(name);
    }


    private void saveRequestLineNameAndValue(String name, String value) {
        httpRequestLineMap.put(name, value);
    }
}
