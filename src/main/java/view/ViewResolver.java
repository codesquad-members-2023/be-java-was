package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ContentType;
import webserver.HttpResponse;

import java.io.IOException;

public class ViewResolver {
    private final static Logger log = LoggerFactory.getLogger("ViewResolver");
    public static void resolveView(String uri, ContentType contentType, HttpResponse httpResponse) throws IOException {
        byte[] body = httpResponse.readFile(contentType.getRootPath(), uri);
        httpResponse.response200Header(body.length, contentType);
        httpResponse.responseBody(body);
    }
}
