package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponseBuilder;
import response.NotFoundResponseBuilder;
import response.RedirectResponseBuilder;
import response.RightResponseBuilder;
import util.SingletonContainer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String templatePath = "src/main/resources/templates";
    private static final String staticPath = "src/main/resources/static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        Path path;

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpRequest httpRequest = new HttpRequest(br);

            String returnUrl = orderToController(httpRequest);

            path = Stream.of(Paths.get(templatePath, returnUrl), Paths.get(staticPath, returnUrl))
                    .filter(Files::exists)
                    .findFirst()
                    .orElse(Paths.get(templatePath, "/error.html"));

            byte[] body = Files.readAllBytes(path);

            HttpResponseBuilder httpResponseBuilder;

            DataOutputStream dos = new DataOutputStream(out);

            logger.info("RRRRRReturn URL={}", returnUrl);
            if (returnUrl.startsWith("redirect")) {
                httpResponseBuilder = new RedirectResponseBuilder(httpRequest);
            } else if (returnUrl.startsWith("/error")) {
                httpResponseBuilder = new NotFoundResponseBuilder(httpRequest);
            } else {
                httpResponseBuilder = new RightResponseBuilder(httpRequest);
            }

            httpResponseBuilder.buildResponse(dos, httpRequest.getExtension(), body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String orderToController(HttpRequest httpRequest) {

        String resourceUrl = httpRequest.getValueByNameInRequestLine("resourceUrl");

        if (resourceUrl.startsWith("/user")) {
            return SingletonContainer.getUserController().mapToFunctions(httpRequest);
        }

        return httpRequest.getValueByNameInRequestLine("resourceUrl");
    }
}

