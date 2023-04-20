package controller;

import annotation.MethodType;
import annotation.RequestMapping;
import request.HttpRequest;
import response.HttpResponse;

@RequestMapping(url = "/")
public class HomeController extends Controller {

    /**
     * Home 아이콘에 비회원 Or 회원 ID를 표시해주는 기능
     * 브라우저에서 쿠키 값을 남겨두는 경우가 있어서 쿠키 값은 있지만 세션 DB의 값과는 일치하지 않는 경우가 발생해 복잡하게 예외처리했는데 리팩토링 필요..
     * @param httpRequest
     * @param httpResponse
     * @return
     */
    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
