package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import response.ContentsType;

public class ModelAndView {

    private String viewName;
    private ContentsType contentsType;
    private Map<String, Object> model = new HashMap<>();
    private static final String TEMPLATE_DIR = "src/main/resources/templates";
    private static final String STATIC_DIR = "src/main/resources/static";

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

    public boolean isDynamicFile() {
        File templateFile = new File(TEMPLATE_DIR + viewName);
        if (templateFile.exists()) {
            return true;
        }
        return false;
    }

    public boolean isStaticFile() {
        File staticFile = new File(STATIC_DIR + viewName);
        if (staticFile.exists()) {
            return true;
        }
        return false;
    }

    public File getFile() throws FileNotFoundException {
        if (isDynamicFile()) {
            return new File(TEMPLATE_DIR + viewName);
        }
        if (isStaticFile()) {
            return new File(STATIC_DIR + viewName);
        }
        throw new FileNotFoundException("파일이 존재하지 않습니다.");
    }

    public boolean hasBody() {
        return contentsType != null ? true : false;
    }
}
