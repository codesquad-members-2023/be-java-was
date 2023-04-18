package view;

import webserver.protocol.ContentType;

import java.util.List;

public class ModelAndView {

    private Model model;
    private View view;

    public ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public void addObject(String key, Object o) {
        model.addObject(key, o);
    }

    public List<String> getObjectKeys() {
        return  model.getKeys();
    }

    public <T>T getObject(String key, Class<T> type) {
        return type.cast(model.getObject(key)
                .orElseThrow(() -> new RuntimeException(key + "를 찾을 수 없습니다.")));
    }

    public static ModelAndView of(String returnPage, Model model) {
        return new ModelAndView(model, View.from(returnPage));
    }

    public void setReturnView(String path) {
        view = View.from(path);
    }

    public boolean isModelPresent() {
        return model.size() > 0;
    }

    public String getViewPath() {
        return view.getPath();
    }

    public ContentType getViewType() {
        return view.getContentType();
    }

    public int getModelSize() {
        return model.size();
    }
}
