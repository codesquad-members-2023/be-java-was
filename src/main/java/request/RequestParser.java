package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public String[] parseRequestLine(String line) {
        String[] splitRequestLine = line.split(" ");
        logger.info(">> RequestParser -> splitRequestLine: {}", Arrays.toString(splitRequestLine));
        return splitRequestLine;
    }

}
