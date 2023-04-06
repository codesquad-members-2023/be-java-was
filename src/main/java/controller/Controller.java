package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

public interface Controller {
    void run(HttpRequest httpRequest, HttpResponse httpResponse);
}
