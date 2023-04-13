package webserver;

import java.io.DataOutputStream;

public interface HttpResponseBuilder {

    void buildResponse(DataOutputStream dos, int lengthOfBodyContent, String extension);
    void responseBody(DataOutputStream dos, byte[] body);
}
