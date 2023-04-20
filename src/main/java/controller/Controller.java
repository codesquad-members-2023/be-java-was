package controller;

import view.ModelAndView;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

public interface Controller {
    ModelAndView service(HttpRequest httpRequest, HttpResponse httpResponse);
}
