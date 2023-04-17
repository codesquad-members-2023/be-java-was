package response;

import java.io.DataOutputStream;

public interface HttpResponseBuilder {

    void buildResponse(DataOutputStream dos, String extension, byte[] body);
//    void responseBody(DataOutputStream dos, byte[] body);
}
