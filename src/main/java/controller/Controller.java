package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

public interface Controller {
    void run(HttpRequest httpRequest, HttpResponse httpResponse);
}
