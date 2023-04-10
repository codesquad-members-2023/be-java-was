package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
