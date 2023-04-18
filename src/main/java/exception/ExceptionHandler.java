package exception;

import view.Model;

import java.util.Map;
import java.util.Optional;

public class ExceptionHandler {

    private static final String DEFAULT_RETURN_PAGE = "/error.html";

    private static final Map<Class<? extends Exception>, String> map = Map.of(
            UserJoinFailEmptyInput.class, "/user/form_failed.html",
            LoginFailNotValidUser.class, "/user/login_failed.html",
            UserJoinDuplicateKey.class, "/user/form_failed.html"
    );

    public static String handle(Model model, Exception e) {
        model.addObject("error", ExceptionMessageBox.from(e.getMessage()));

        return Optional.ofNullable(map.get(e.getClass()))
                .orElse(DEFAULT_RETURN_PAGE);
    }
}
