package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private Map<String,String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void forward(String url){
        try{
            byte[] body = Files.readAllBytes(new File("src/main/resources/static" + url).toPath());
            if(url.endsWith(".css")){
                headers.put("Content-Type","text/css");
            }
            response200Header(body.length);
            responseBody(body);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + headers.get("Content-Type")+ "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            getKey();
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void getKey(){
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + "key : " + headers.get(key) + "/r/n");
            }
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
