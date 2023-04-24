package controller;

import annotation.RequestMapping;
import servlet.HttpRequest;

import static webserver.RequestHandler.logger;

@RequestMapping(path = "/")
public class DefaultController implements Controller {

}
