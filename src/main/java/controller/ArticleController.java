package controller;

import annotation.PathVariable;
import annotation.RequestMapping;
import model.Article;
import service.ArticleService;
import servlet.HttpRequest;
import type.RequestMethod;
import view.ModelAndView;

import java.util.Collection;
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

    @RequestMapping(path = "/show/{articleId}")
    public String showArticle(@PathVariable(name = "articleId") int articleId,
                              ModelAndView modelAndView) {

        Article article = articleService.findById(articleId);
        modelAndView.addModel("article", article);
        return "/qna/show";
    }

    @RequestMapping(path = "/list")
    public String showList(ModelAndView modelAndView) {
        Collection<Article> articles = articleService.findAll();
        modelAndView.addModel("articles", articles);
        return "/index";
    }

    @RequestMapping(path = "/form")
    public String show() {
        return "/qna/form";
    }
    
}
