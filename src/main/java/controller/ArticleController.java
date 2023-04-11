package controller;

import request.HttpRequest;

/**
 * 1안. Controller의 Function Map으로 실행할 함수를 가지고 있음 -> 단점, 가독성이 별로.
 * 2안. Annotation으로 Mapping해주도록하기
 */
public class ArticleController implements Controller {


    @Override
    public String doGet(HttpRequest httpRequest) {
        httpRequest.setUrl("/index.html");

        return httpRequest.getUrl();
    }


}
