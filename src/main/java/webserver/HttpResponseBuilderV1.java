package webserver;

import java.io.DataOutputStream;

public interface HttpResponseBuilderV1 {

    void buildResponse(DataOutputStream dos, int lengthOfBodyContent, String extension);
    void responseBody(DataOutputStream dos, byte[] body);
}
