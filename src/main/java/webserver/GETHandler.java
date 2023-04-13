package webserver;

import model.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtils;
import util.StylesheetUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GETHandler {
    private static final Logger logger = LoggerFactory.getLogger(GETHandler.class);

    public static void doGet(String url, DataOutputStream dos) throws IOException {
        Stylesheet stylesheet = new Stylesheet(StylesheetUtils.getContentType(url),
                StylesheetUtils.getPathName(url) + url);

        logger.debug("stylesheet: {}, {}", stylesheet.getContentType(), stylesheet.getPathName());

        byte[] body = Files.readAllBytes(new File(stylesheet.getPathName()).toPath());
        HttpResponseUtils.response200Header(dos, body.length, stylesheet.getContentType());
        HttpResponseUtils.responseBody(dos, body);
    }
}
