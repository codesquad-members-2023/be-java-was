package view;

import java.util.HashMap;
import java.util.Map;

import response.ContentsType;

public class ModelAndView {

    private String viewName;
    private ContentsType contentsType;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setViewName(String viewName) {
        this.viewName = viewName;
        return this;
    }

    public ModelAndView setContentsType(ContentsType contentsType) {
        this.contentsType = contentsType;
        return this;
    }

    public Object getModelAttribute(String key) {
        return model.get(key);
    }

    public void setModelAttribute(String key, Object value) {
        model.put(key, value);
    }


    public String getPath() {
        return contentsType.getLocatedPath() + viewName;
    }

    public boolean hasBody() {
        return contentsType != null ? true : false;
    }
}
