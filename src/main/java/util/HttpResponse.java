package util;

/**
 * 이 클래스는 Response에 대한 메타 데이터를 저장합니다.
 * Body는 메모리 용량에 부담이 될 것 같아 buffer를 통해서만 전달됩니다.
 */
public class HttpResponse {

    String httpVersion;
    String status;
    String statusMessage;
    HttpHeaders httpHeaders;

    public HttpResponse(String httpVersion, String status, String statusMessage, HttpHeaders httpHeaders) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.statusMessage = statusMessage;
        this.httpHeaders = httpHeaders;
    }

    /**
     * Response의 첫 줄을 return합니다.
     * @return responseHeadLine
     */
    public String getHeadLine() {
        return String.join(" ", httpVersion, status, statusMessage);
    }

    public String getHeaders() {
        return httpHeaders.toString();
    }

    @Override
    public String toString() {
        String headLine = String.join(" ", httpVersion, status, statusMessage);
        return headLine + "\r\n" + httpHeaders;
    }
}
