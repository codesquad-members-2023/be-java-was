package view;

import java.io.IOException;

import response.ContentsType;
import response.HttpHeaders;
import response.HttpResponse;
import response.Status;

public class ViewResolver {

    /**
     * View 이름을 인자로 받아 Response를 작성합니다.
     *
     * @throws IOException
     */

    public byte[] mapView(String viewName, HttpResponse httpResponse) throws IOException {
        //Redirect 키워드가 있는 경우 redirect 헤더로 작성
        if (viewName.startsWith("redirect:")) {
            String redirectViewName = viewName.replace("redirect:", "");

            View redirectView = new View(redirectViewName);

            httpResponse.setHttpVersion("HTTP/1.1")
                    .setStatus(Status.FOUND)
                    .addHeader("Location", redirectViewName)
                    .setView(redirectView);

            return httpResponse.render();
        }

        //그 외의 경우 200 OK 응답을 반환
        ContentsType contentsType = mapTypeByIndentifier(viewName);
        View okView = new View(viewName, contentsType);

        httpResponse.setHttpVersion("HTTP/1.1")
                .setStatus(Status.OK)
                .addHeader("Content-Type", contentsType.getContentType())
                .setView(okView);

        return httpResponse.render();
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
