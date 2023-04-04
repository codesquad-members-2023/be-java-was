package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputRequestParser {
    private final List<String> entireRequest;
    private static final Logger logger = LoggerFactory.getLogger(InputRequestParser.class);
    private static final int FIRST_REQUEST_LINE = 0;
    private static final int START_REQUEST_HEADER = 1;

    public InputRequestParser(InputStream in) {
        this.entireRequest = generateEntireRequest(in);
    }

    private List<String> generateEntireRequest(InputStream inputStream) {
        List<String> entireRequest = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while (!(line = br.readLine()).equals("")) {
                entireRequest.add(line);
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return entireRequest;
    }

    public RequestLine getRequestLine() {
        String[] splitRequestLine = entireRequest.get(FIRST_REQUEST_LINE).split(" ");
        return new RequestLine(splitRequestLine[0], splitRequestLine[1], splitRequestLine[2]);
    }

    public RequestHeader getRequestHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i = START_REQUEST_HEADER; i < entireRequest.size(); i++) {
            sb.append(entireRequest.get(i));
        }
        return new RequestHeader(sb.toString());
    }
}
