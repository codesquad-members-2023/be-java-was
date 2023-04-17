package request;

import util.RequestParser;
import util.SingletonContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequestLine {

    private HashMap<String, String> httpRequestLineMap = new HashMap<>();

    public HttpRequestLine(BufferedReader br) throws IOException {

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

            saveRequestLineNameAndValue("httpMethod", httpMethod);
            saveRequestLineNameAndValue("resourceUrl", resourceUrl);
            saveRequestLineNameAndValue("returnUrl", returnUrl);
        }
    }

    public String getValueByNameInRequestLine(String name) {
        return httpRequestLineMap.get(name);
    }

    public String getExtension() {
        String fileName = httpRequestLineMap.get("returnUrl");
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    private void saveRequestLineNameAndValue(String name, String value) {
        httpRequestLineMap.put(name, value);
    }
}
