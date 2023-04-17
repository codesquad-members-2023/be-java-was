package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequestHeader {
    private HashMap<String, String> header;

    public HttpRequestHeader(BufferedReader br) throws IOException {

        this.header = new HashMap<>();

        String requestHeaders = br.readLine();

        while (!requestHeaders.equals("")) {
            String separator = ":";
            int separatorIdx = requestHeaders.indexOf(separator);
            String headersName = requestHeaders.substring(0, separatorIdx).trim();
            String headersValue = requestHeaders.substring(separatorIdx + 1).trim();

            saveHeaderNameAndValue(headersName, headersValue);
            requestHeaders = br.readLine();
        }
    }

    private void saveHeaderNameAndValue(String name, String value) {
        header.put(name, value);
    }

    public String getValueByNameInHeader(String name) {
        return header.get(name);
    }
}
