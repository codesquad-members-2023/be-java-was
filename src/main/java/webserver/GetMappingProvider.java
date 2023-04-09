package webserver;

import db.Database;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetMappingProvider {
    private static final Logger logger = LoggerFactory.getLogger(GetMappingProvider.class);

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
            if (words.contains("Index") && i == words.size() - 1) {
                sb.append(".html");
                break;
            }
            if (words.contains("Form") && i == words.size() - 1) {
                sb.append(".html");
                break;
            }
            if (words.contains("Styles") && i == words.size() - 1) {
                sb.append(".css");
                break;
            }
            if (words.contains("Bootstrap") && words.contains("Js") && i == words.size() - 1) {
                sb.append(".min");
                sb.append(".js");
                break;
            }
            if (words.contains("Bootstrap") && words.contains("Css") && i == words.size() - 1) {
                sb.append(".min");
                sb.append(".css");
                break;
            }
            if (words.contains("Jquery") && words.contains("Js") && i == words.size() - 1) {
                sb.append("-2.2.0.min.js");
                break;
            }
            if (words.contains("Scripts") && words.contains("Js") && i == words.size() - 1) {
                sb.append(".js");
                break;
            }
            if (words.contains("Fonts") && i == words.size() - 1) {
                sb.append("-halflings-regular.woff");
                break;
            }
        }
        return sb.toString();
    }

    public String mappingIndex() {
        return "src/main/resources/templates/index.html";
    }

    public String mappingUserForm() {
        return "src/main/resources/templates/user/form.html";
    }

    public String mappingUserCreate(String queryString) {
        try {
            Class<?> loadClass = User.class;
            Constructor<?> constructor = loadClass.getDeclaredConstructor(String.class, String.class, String.class,
                    String.class);
            String[] queryParams = URLDecoder.decode(queryString, StandardCharsets.UTF_8).split("&");
            Map<String, String> params = new HashMap<>();
            for (String queryParam : queryParams) {
                String[] pair = queryParam.split("=");
                params.put(pair[0], pair[1]);
            }
            User user = (User) constructor.newInstance(params.get("userId"), params.get("password"), params.get("name"),
                    params.get("email"));
            Database.addUser(user);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
        logger.info("DB 확인 : " + Database.findAll());
        return "redirect:src/main/resources/templates/index.html";
    }
}
