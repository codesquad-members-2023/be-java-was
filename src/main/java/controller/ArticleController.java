package controller;

import annotation.RequestMapping;
import model.Article;
import model.User;
import service.ArticleService;
import servlet.HttpRequest;
import type.RequestMethod;
import view.ModelAndView;

import java.util.Map;

@RequestMapping(path = "/qna")
public class ArticleController implements Controller{

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(HttpRequest httpRequest) throws Exception {
        Map<String, String> requestBody = httpRequest.getRequestBody();
        articleService.save(requestBody);
        return "redirect:/";
    }

    @RequestMapping()
    public String show(HttpRequest httpRequest) {
        return httpRequest.getPathInfo();
    }

}
