package controller;

import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import request.HttpRequest;
import response.HttpResponse;

@RequestMapping(url = "/users/list")
public class UserListController extends Controller {
    @MethodType(value = "GET")
    public String userList(HttpRequest httpRequest, HttpResponse httpResponse) {
        //유저 목록 조회
        if (!Database.getUserList().isEmpty()) {
            httpResponse.setModelAttribute("users", Database.getUserList());
        }
        return "/user/list.html";
    }
}
