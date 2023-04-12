package controller;

import protocol.HttpRequest;
import protocol.HttpResponse;

public interface Controller {
    String service(HttpRequest httpRequest, HttpResponse httpResponse);
}
