package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import response.ContentsType;

public class ModelAndView {

    private String viewName;
    private ContentsType contentsType;
    private Map<String, String> model = new HashMap<>();

    public ModelAndView setViewName(String viewName) {
        this.viewName = viewName;
        return this;
    }

    public ModelAndView setContentsType(ContentsType contentsType) {
        this.contentsType = contentsType;
        return this;
    }

    /**
     * httpResponse에서 header 정보를 읽어와 첫 줄과 header 메시지를 작성하고,
     * viewName으로 파일 경로에 있는 파일의 Body와 합쳐 반환한다.
     * @return httpResponseMessage
     * @throws IOException
     */



    public String getPath() {
        return contentsType.getLocatedPath() + viewName;
    }

    public boolean hasBody() {
        return contentsType != null ? true : false;
    }
}
