package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;
import model.InputRequestParser;
import model.RequestLine;
import model.Response;
import model.UrlMethodMappingGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestLine requestLine = handleRequest(in);
            sendResponse(out, requestLine);
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestLine handleRequest(InputStream in) {
        InputRequestParser inputRequestParser = new InputRequestParser(in);
        RequestLine requestLine = inputRequestParser.getRequestLine();
        logger.info(requestLine.toString());
        return requestLine;
    }

    private void sendResponse(OutputStream out, RequestLine requestLine)
            throws InvocationTargetException, IllegalAccessException, IOException {
        Map<String, Method> urlMethodMapping = UrlMethodMappingGenerator.generateUrlMethodMapping();
        Response response = new Response(new DataOutputStream(out));
        DataOutputStream dos = response.generateDataOutputStream(urlMethodMapping, requestLine);
    }
}
