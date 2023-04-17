package controller;

import java.util.Optional;

import annotation.MethodType;
import annotation.RequestMapping;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import session.SessionDb;

@RequestMapping(url = "/")
public class HomeController extends Controller {

    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        Optional<String> sessionId = httpRequest.getSessionId();
        if (sessionId.get() != null) {
            User user = SessionDb.getUserBySessionId(sessionId.get());
            httpResponse.setModelAttribute("userId", user.getUserId());
        }

        return "/index.html";
    }
}
