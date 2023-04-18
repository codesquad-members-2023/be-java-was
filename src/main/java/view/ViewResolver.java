package view;

import exception.ExceptionMessageBox;
import webserver.protocol.ContentType;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;
import webserver.protocol.response.StatusCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static util.Constants.NEW_LINE;
import static view.ViewTemplate.START_TAG;
import static webserver.protocol.response.HttpResponse.REDIRECT_KEY;

public class ViewResolver {
    private final DataOutputStream dos;

    public ViewResolver(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public static ViewResolver create(OutputStream out) {
        return new ViewResolver(out);
    }

    public void render(HttpResponse httpResponse, ModelAndView mv) throws IOException {
        try {

            String viewPath = mv.getViewPath();
            ContentType viewType = mv.getViewType();

            if (!viewPath.contains(REDIRECT_KEY)) {
                String viewString = new String(renderView(viewPath, viewType), StandardCharsets.UTF_8);

                if (viewString.contains(START_TAG)) {
                    viewString = ViewTemplate.renderViewWithModel(viewString, mv);
                }
                httpResponse.sendForward(viewString.getBytes(), viewType);
            }

            if (viewPath.contains(REDIRECT_KEY)) {
                httpResponse.sendRedirect(viewPath);
            }

            resolve(httpResponse);

        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            httpResponse.setStatus(StatusCode.NOT_FOUND);
            mv.addObject("error", ExceptionMessageBox.from("페이지를 찾을 수 없습니다."));
            mv.setReturnView("/error.html");

            render(httpResponse, mv);
        }
    }

    private void resolve(HttpResponse httpResponse) throws IOException {
        dos.write(httpResponse.getMessage().getBytes(StandardCharsets.UTF_8));
        dos.write(NEW_LINE.getBytes());
        dos.flush();
    }

    private byte[] renderView(String path, ContentType type) throws IOException {
        return Files.readAllBytes(new File(type.getTypeDirectory() + path).toPath());
    }

}
