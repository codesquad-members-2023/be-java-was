package controller;

import exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Model;
import view.ModelAndView;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;

public abstract class FrontController implements Controller {
    protected Logger logger = LoggerFactory.getLogger(FrontController.class);

    /**
     * 작업을 처리할 메서드를 호출한다.
     *
     * @param httpRequest
     * @param httpResponse
     */
    public ModelAndView service(HttpRequest httpRequest, HttpResponse httpResponse) {
        Model model = Model.create();
        String returnPage = httpRequest.getUrlPath();
        try {
            switch (httpRequest.getMethod()) {
                case GET:
                    returnPage = doGet(httpRequest, httpResponse, model);
                    break;
                case POST:
                    returnPage = doPost(httpRequest, httpResponse);
                    break;
            }
        } catch (java.lang.Exception e) {
            logger.error(e.getMessage());
            returnPage = ExceptionHandler.handle(model, e);
        }
        return ModelAndView.of(returnPage, model);
    }

    protected abstract String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException;
    protected abstract String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
