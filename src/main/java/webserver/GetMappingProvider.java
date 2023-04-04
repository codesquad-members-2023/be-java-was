package webserver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMappingProvider {
    public String methodNameToURL(String methodName) {
        String name = methodName.replaceAll("^mapping", "");
        Pattern pattern = Pattern.compile("[A-Za-z][a-z]*");
        Matcher matcher = pattern.matcher(name);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            sb.append("/");
            sb.append(words.get(i).toLowerCase());
            if (i == words.size() - 1) {
                sb.append(".html");
            }
        }
        return sb.toString();
    }

    public String mappingIndex() {
        return "src/main/resources/templates";
    }

    public String mappingUserForm() {
        return "src/main/resources/templates/user";
    }

    public String mappingUserCreate() {

        return "redirect:src/main/resources/templates/index.html";
    }
}
