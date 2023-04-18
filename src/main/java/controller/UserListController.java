package controller;

import java.util.Optional;

import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import model.User;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;
import session.SessionDb;

@RequestMapping(url = "/users/list")
public class UserListController extends Controller {
    @MethodType(value = "GET")
    public String userList(HttpRequest httpRequest, HttpResponse httpResponse) {
        Optional<String> sessionId = httpRequest.getSessionId();
        if (!sessionId.isEmpty()) {
            String parsedSessionId = HttpRequestUtils.parseSessionId(sessionId.get());

            if (SessionDb.getUserBySessionId(parsedSessionId) == null) {
                return "redirect:/user/login.html";
            }
        }
        else {
            return "redirect:/user/login.html";
        }
            //유저 목록 조회
        if (!Database.getUserList().isEmpty()) {
            httpResponse.setModelAttribute("users", Database.getUserList());
        }
        return "/user/list.html";
    }
}
