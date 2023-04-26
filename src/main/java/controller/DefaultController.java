package controller;

import annotation.RequestMapping;
import model.Article;
import org.checkerframework.checker.units.qual.A;
import service.ArticleService;
import servlet.HttpRequest;
import view.ModelAndView;

import java.util.Collection;

import static webserver.RequestHandler.logger;

@RequestMapping(path = "/")
public class DefaultController implements Controller {

}
