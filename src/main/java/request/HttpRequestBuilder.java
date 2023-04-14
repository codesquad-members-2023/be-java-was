package request;

import java.io.BufferedReader;
import java.io.IOException;

import response.HttpHeaders;

public class HttpRequestBuilder {
    public static HttpRequest setup(BufferedReader br) throws IOException {
        //HTTP Request Line을 읽어옵니다.
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.initRequestLine(br.readLine());

        //HTTP Request Header를 읽어옵니다.
        String requestHeader;
        HttpHeaders httpRequestHeaders = new HttpHeaders();
        while (!(requestHeader = br.readLine()).equals("")) {
            //Request Header 객체에 삽입
            httpRequestHeaders.parse(requestHeader);
        }
        httpRequest.setHttpHeaders(httpRequestHeaders);

        //HTTP Request Body를 읽어옵니다.
        if (httpRequestHeaders.getContentLength() > 0) {
            char[] buffer = new char[httpRequestHeaders.getContentLength()];

            int byteRead = br.read(buffer, 0, httpRequestHeaders.getContentLength());
            //HttpRequest 객체에 Request Body 추가
            httpRequest.setBody(new String(buffer, 0, byteRead));
        }

        return httpRequest;
    }
}
