package controller.user;

import controller.FrontController;
import exception.UserJoinDuplicateKey;
import exception.UserJoinFailEmptyInput;
import service.UserService;
import view.Model;
import webserver.protocol.response.StatusCode;
import util.ProtocolParser;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class UserJoinController extends FrontController {

    UserService userService;

    public UserJoinController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        return httpRequest.getUrlPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return join(httpRequest, httpResponse);
    }



    private String join(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
            userService.join(parameter);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            httpResponse.setStatus(StatusCode.BAD_REQUEST);
            throw new UserJoinFailEmptyInput("요청 값을 모두 입력해 주세요.");
        } catch (IllegalStateException e) {
            httpResponse.setStatus(StatusCode.BAD_REQUEST);
            throw new UserJoinDuplicateKey("중복된 아이디가 있습니다.");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

}
