package view;

import servlet.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface View {

    public String getName();
    public void render(Map<String, ?> model, HttpResponse httpResponse) throws IOException;
}
