package view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import response.ContentsType;
import response.HttpHeaders;
import response.HttpResponse;
import response.Status;

public class ViewResolver {

    /**
     * View 이름을 인자로 받아 Response를 작성합니다.
     *
     * @param out
     * @throws IOException
     */

    public byte[] mapView(String viewName) throws IOException {
        //Redirect 키워드가 있는 경우 redirect 헤더로 작성
        if (viewName.startsWith("redirect:")) {
            String redirectViewName = viewName.replace("redirect:", "");

            View redirectView = new View(redirectViewName, new HttpResponse("HTTP/1.1", Status.FOUND,
                new HttpHeaders(Map.of("Location", redirectViewName))));
            return redirectView.render();
        }

        //그 외의 경우 200 OK 응답을 반환
        ContentsType contentsType = mapTypeByIndentifier(viewName);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", contentsType.getContentType());
        View okView = new View(viewName, contentsType, new HttpResponse("HTTP/1.1",Status.OK,
            new HttpHeaders(header)));

        return okView.render();
    }


    /**
     * viewName의 파일 확장자를 기준으로 일치하는 content-type을 반환합니다.
     *
     * @param view
     * @return
     * @throws IOException
     */
    private ContentsType mapTypeByIndentifier(String view) {
        //Static 파일 경로에서 탐색
        for (ContentsType contentsType : ContentsType.values()) {
            if (view.matches(contentsType.getIdentifier())) {
                return contentsType;
            }
        }
        throw new IllegalArgumentException("Match되는 Contents-Type이 존재하지 않습니다.");
    }

}
