package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);
    private final String WELCOME_PAGE = "/index.html";

    public String[] parseRequestLine(String line) {
        String[] splitRequestLine = line.split(" ");
        logger.info(">> RequestParser -> splitRequestLine: {}", Arrays.toString(splitRequestLine));
        // 만약 localhost:8080만 입력됐을 시
        if (splitRequestLine[1].equals("/")) {
            splitRequestLine[1] = WELCOME_PAGE;
        }
        return splitRequestLine;
    }

}
