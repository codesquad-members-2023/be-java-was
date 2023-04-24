package interceptor;

import model.User;
import servlet.HttpRequest;
import servlet.HttpResponse;
import session.SessionManager;
import util.MyHandler;

public class LoginCheckInterceptor implements Interceptor{
    private SessionManager sessionManager;
    public LoginCheckInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, MyHandler handlerMethod) {

        String pathInfo = httpRequest.getPathInfo();

        // TODO : 이런 하드 코딩은 지양해야 한다.
        if (!pathInfo.equals("/user/list")) {
            return true;
        }

        User user = (User)sessionManager.getSession(httpRequest);

        if (user == null ) {
            httpResponse.sendRedirect("redirect:/user/login.html");
            return false;
        }
        return true;
    }
}
