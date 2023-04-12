package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ContentTypeParser {

    private Logger log = LoggerFactory.getLogger(getClass());

    public String getPriorityContentType(String accept) {

        if (accept.equals("*/*")) {
            return null;
        }

        String priorityContentType = accept.split(",")[0];

        log.info("Content Type = {}", priorityContentType);
        return priorityContentType;
    }

    private String getPriorityContentType(HashMap<Double, String> typeMap) {

        List<Map.Entry<Double, String>> typeList = new LinkedList<>(typeMap.entrySet());
        typeList.sort((o1, o2) -> o2.getKey().compareTo(o1.getKey()));

        return typeList.get(0).getValue();
    }
}
