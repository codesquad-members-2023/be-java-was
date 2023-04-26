package view;

import java.io.File;
import java.util.Collection;

public class ViewResolver {

    private final String ROOT_PATH = "src/main/resources/";
    private final String[] BASE_PATH = {"static", "templates"};
    private final String ERROR_404 = "static/errorPage-404.html";
    private final String REDIRECT = "redirect:";

    private Collection<View> views;


    public ViewResolver(Collection<View> views) {
        this.views = views;
    }

    public View resolve(String viewName) {
        for (View view : views) {

            if (viewName.startsWith(REDIRECT)) {
                return new MyView();
            }

            if (!view.getName().equals(viewName)) {
                continue;
            }
            // TODO : 현재는 굉장히 비효율적이다.
            File file = getFileAt(view.getPath());
            return new MyView(viewName, file.getPath());
        }

        return new MyView("", ROOT_PATH + ERROR_404);
    }

    private File getFileAt(String fileName) {
        for (String basePath : BASE_PATH) {
            String filePath = ROOT_PATH + basePath + fileName;
            if (!fileExistsAt(filePath)) {
                continue;
            }
            return new File(filePath);
        }
        return null;
    }

    private boolean fileExistsAt(String filepath) {
        File f = new File(filepath);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }
}
