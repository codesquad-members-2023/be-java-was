package servlet;

import annotation.RequestMapping;

@RequestMapping
public interface HttpServlet {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
