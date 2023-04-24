package view;


import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String viewName;
    private Map<String, Object> model;

    public ModelAndView() {
        model = new HashMap<>();
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName , Map<String, ?> model) {
        this.viewName = viewName;
        this.model = new HashMap<>(model);
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void addModel(String name, Object value) {
        model.put(name,value);
    }
}
