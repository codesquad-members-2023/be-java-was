package response;

/**
 * 이 클래스는 Response에 대한 메타 데이터를 저장합니다.
 * Body는 메모리 용량에 부담이 될 것 같아 buffer를 통해서만 전달됩니다.
 */
public class HttpResponse {

    String httpVersion;
    Status status;
    HttpHeaders httpHeaders;

    public HttpResponse(String httpVersion, Status status, HttpHeaders httpHeaders) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.httpHeaders = httpHeaders;
    }


    @Override
    public String toString() {
        String headLine = String.join(" ", httpVersion, status.getStatusCode(), status.getStatusMessage());
        return headLine + "\r\n" + httpHeaders;
    }
}
