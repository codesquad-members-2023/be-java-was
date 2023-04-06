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
     * View의 위치에 있는 파일을 찾아 HTTP 응답으로 보냅니다.
     *
     * @param out
     * @throws IOException
     */

    public byte[] mapView(OutputStream out, String viewName) throws IOException {
        View view;
        //Redirect 키워드가 있는 경우 redirect 헤더로 작성
        if (viewName.startsWith("redirect:")) {
            String redirectView = viewName.replace("redirect:", "");

            view = new View(redirectView, new HttpResponse("HTTP/1.1", Status.FOUND,
                new HttpHeaders(Map.of("Location", redirectView))));
            return view.render();
        }

        //그 외의 경우 200 OK 응답을 반환
        ContentsType contentsType = mapTypeByIndentifier(viewName);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", contentsType.getContentType());
        view = new View(viewName, contentsType, new HttpResponse("HTTP/1.1", Status.OK,
            new HttpHeaders(header)));

        return view.render();
    }


    /**
     * css, js, fonts는 static 폴더에서 파일을 탐색하여 반환합니다. (content header를 text/css로 변경해서 보냅니다.) 나머지(html
     * 파일)는 templates 폴더에서 탐색합니다.
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
