package view.viewTemplate;

import view.Model;
import view.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.Constants.EMPTY;
import static view.viewTemplate.TemplateConstans.*;
import static view.viewTemplate.TemplateParser.formatted;
import static webserver.RequestHandler.logger;

public class ViewTemplate {
    private String before;  // 변경할 String + 변경할 문구를 표시하는 태그 : {{#user}}변경할 내용{{/user}}
    private String after;   // 변경할 String (태그가없다.) ex : 변경할 내용
    private String unless;  // 만약 해당 객체가 없을 때 대신 표시되는 구역 ex : {{^user}}표시할 내용{{/^user}}
    private String key; // model value를 찾을 key

    public ViewTemplate(String before, String after, String unless, String key) {
        this.before = before;
        this.after = after;
        this.unless = unless;
        this.key = key;
    }

    /**
     *
     * @param httpResponse 반환할 body
     * @param model and view
     * @return model 데이터를 포함한 httpResponse 반환할 body
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String renderViewWithModel(String viewString, ModelAndView mv) throws InvocationTargetException, IllegalAccessException {
        if (mv.getModelSize() > 0) {
            viewString = viewString.replace(before, renderModel(after, mv.getModel()));
            viewString = deleteUnless(viewString);
        }

        if(mv.getModelSize() == 0) {
            viewString = viewString.replace(before, EMPTY);
            viewString = renderUnless(viewString);
        }

        return viewString;
    }

    private String deleteUnless(String viewString) {
        if (unless!=null) {
            return viewString.replace(unless, EMPTY)
                    .replace(TemplateParser.getTag(viewString, UNLESS_TAG), EMPTY)
                    .replace(TemplateParser.getTag(viewString, UNLESS_END_TAG), EMPTY);
        }
        return viewString;
    }

    private String renderUnless(String viewString) {
        if (unless!=null) {
            viewString = viewString.replace(TemplateParser.getTag(viewString, UNLESS_TAG), EMPTY)
                    .replace(TemplateParser.getTag(viewString, UNLESS_END_TAG), EMPTY);
            return viewString;
        }
        return viewString;
    }

    public String renderModel(String message, Model model) throws IllegalAccessException, InvocationTargetException {
            logger.debug(key);
            Object o = model.getObject(key)
                    .orElseThrow(() -> new RuntimeException(key + "를 찾을 수 없습니다."));

            if (o instanceof List) {    // object가 리스트 형태일 때
                message = renderModelList(message, key, o);
            } else {
                List<Method> methods = Arrays.stream(o.getClass().getDeclaredMethods())
                        .filter(e -> e.getName().startsWith(GETTER_METHOD_HEADER))
                        .collect(Collectors.toList());

                for (Method m : methods) {
                    message = message.replace(formatted(key, m.getName()), String.valueOf(m.invoke(o)));
                }
            }

        return message;
    }

    private String renderModelList(String message, String key, Object o) throws InvocationTargetException, IllegalAccessException {
        List<?> list = (List<?>) o;
        StringBuilder result = new StringBuilder();
        for (Object item : list) {
            result.append(renderModel(message, Model.from(key, item))); // 하위 요소를 하나씩 추가한다.
        }
        return result.toString();
    }

    public static ViewTemplate from(String view) {
        String key = TemplateParser.parseKey(view);
        String before = TemplateParser.parseBefore(view);
        String after = TemplateParser.parseAfter(view);
        String unless = TemplateParser.parseUnless(view);
        return new ViewTemplate(before, after, unless, key);
    }

}
