package model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.GetMappingProvider;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private DataOutputStream dos;
    private Map<String, String> contentTypeMap;

    public Response(DataOutputStream dos) {
        this.dos = dos;
        this.contentTypeMap = init();
    }

    public DataOutputStream generateDataOutputStream(Map<String, Method> urlMethodMapping, RequestLine requestLine) {
        try {
            if (urlMethodMapping.containsKey(requestLine.getURL())) {
                Method method = urlMethodMapping.get(requestLine.getURL());
                GetMappingProvider getMappingProvider = new GetMappingProvider();
                byte[] body = new byte[0];

                String resultPath = checkQueryString(method, getMappingProvider, requestLine);
                if (resultPath.contains("redirect:")) {
                    resultPath = convertRedirectPath(resultPath);
                    String newUrl = convertRedirectUrl(resultPath);
                    response302Header(dos, body.length, newUrl);
                    responseBody(dos, body);
                    return dos;
                }
                body = Files.readAllBytes(new File(resultPath).toPath());
                response200Header(dos, body.length, requestLine.getURL());
                responseBody(dos, body);
            }
            response404Header(dos);
        } catch (InvocationTargetException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return dos;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String url) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentTypeMap.get(checkContentType(url)) + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("200" + e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent, String newUrl) {
        String redirectMessage = "Redirecting to " + newUrl;
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: " + newUrl + "\r\n");
            dos.writeBytes("Content-Type: text/plain\r\n");
            dos.writeBytes("Content-Length: " + redirectMessage.length() + "\r\n");
            dos.writeBytes("\r\n");
            dos.writeBytes(redirectMessage);
        } catch (IOException e) {
            logger.error("302" + e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: 0\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("404" + e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error("body" + e.getMessage());
        }
    }

    public String checkQueryString(Method method, GetMappingProvider getMappingProvider, RequestLine requestLine)
            throws InvocationTargetException, IllegalAccessException {
        String resultPath;
        if (!requestLine.getQueryString().equals("")) {
            return resultPath = (String) method.invoke(getMappingProvider, requestLine.getQueryString());
        }
        return resultPath = (String) method.invoke(getMappingProvider);
    }

    private String convertRedirectPath(String resultPath) {
        return resultPath.replaceAll("redirect:", "");
    }

    private String convertRedirectUrl(String resultPath) {
        String[] temp = resultPath.split("templates");
        logger.warn(Arrays.toString(temp));
        return temp[1];
    }

    private String checkContentType(String url) {
        if (url != null) {
            int dotIndex = url.lastIndexOf('.');
            if (dotIndex == -1 || dotIndex == url.length() - 1) {
                return "";
            } else {
                return url.substring(dotIndex + 1);
            }
        }
        return "text/html";
    }

    private Map<String, String> init() {
        Map<String, String> contentTypeMap = new HashMap<>();
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("js", "text/javascript");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("woff", "application/octet-stream");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("ico", "image/avif");
        return contentTypeMap;
    }
}
