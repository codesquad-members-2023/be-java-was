package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

public interface Controller {
    String service(HttpRequest httpRequest, HttpResponse httpResponse);
}
