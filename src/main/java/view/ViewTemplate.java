package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static util.Constants.EMPTY;

public class ViewTemplate {
    public static final String START_TAG = "{{#}}";
    public static final String END_TAG = "{{/}}";
    private static final String GETTER_METHOD = "get";
    private static final String TEMPLATE_FORMAT = "{{%s.%s}}";

    public static String renderViewWithModel(String viewString, ModelAndView mv) throws InvocationTargetException, IllegalAccessException {
        int startTag = viewString.indexOf(START_TAG);
        int endOfStartTag = startTag + START_TAG.length();
        int endTag = viewString.indexOf(END_TAG);
        int endOfEndTag = endTag + END_TAG.length();

        String before = viewString.substring(startTag, endOfEndTag);
        String after = viewString.substring(endOfStartTag, endTag);

        if (mv.getModelSize() > 0) {
            viewString = viewString.replace(before, renderModel(after, mv.getModel()));
        }

        if(mv.getModelSize() == 0) {
            viewString = viewString.replace(before, EMPTY);
        }

        return viewString;
    }

    public static String renderModel(String message, Model model) throws IllegalAccessException, InvocationTargetException {
        List<String> keys = model.getKeys();

        for (String key : keys) {
            Object o = model.getObject(key)
                    .orElseThrow(() -> new RuntimeException(key + "를 찾을 수 없습니다."));

            if (o instanceof List) {
                message = renderModelList(message, key, o);
            } else {
                List<Method> methods = Arrays.stream(o.getClass().getDeclaredMethods())
                        .filter(e -> e.getName().startsWith(GETTER_METHOD))
                        .collect(Collectors.toList());

                for (Method m : methods) {
                    message = message.replace(formantted(key, m.getName()), String.valueOf(m.invoke(o)));
                }
            }
        }

        return message;
    }

    private static String renderModelList(String message, String key, Object o) throws InvocationTargetException, IllegalAccessException {
        List<?> list = (List<?>) o;
        StringBuilder result = new StringBuilder();
        for (Object item : list) {
            result.append(renderModel(message, Model.from(key, item)));
        }
        return result.toString();
    }

    private static String formantted(String key, String methodName) {
        return String.format(TEMPLATE_FORMAT, key, methodName);
    }

}
