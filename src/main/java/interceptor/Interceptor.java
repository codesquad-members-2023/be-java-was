package interceptor;

import servlet.HttpRequest;
import servlet.HttpResponse;
import util.MyHandler;

public interface Interceptor {
    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, MyHandler handlerMethod);
}
