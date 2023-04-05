package webserver;

import java.io.*;

public class HttpRequest {
    public static String startLine(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return br.readLine();
    }
}
