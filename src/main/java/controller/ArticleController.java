package controller;

import annotation.RequestMapping;
import request.HttpRequest;

@RequestMapping(url = "/")
public class ArticleController implements Controller {


    @Override
    public String doGet(HttpRequest httpRequest) {
        httpRequest.setUrl("/index.html");

        return httpRequest.getUrl();
    }


}
