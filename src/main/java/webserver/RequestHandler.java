package webserver;

import controller.UrlMapper;
import controller.UserController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import view.ViewResolver;

public class RequestHandler implements Runnable {

    private UrlMapper urlMapper;
    private ViewResolver viewResolver;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(UrlMapper urlMapper, ViewResolver viewResolver, Socket connection) {
        this.urlMapper = urlMapper;
        this.connection = connection;
        this.viewResolver = viewResolver;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpRequest httpRequest = new HttpRequest();
            httpRequest.init(br.readLine());
            logger.debug("http Request : {}", httpRequest);

            String requestHeader;
            while (!(requestHeader = br.readLine()).equals("")) {
                logger.debug("header : {}", requestHeader);
            }

            String viewName = urlMapper.requestMapping(httpRequest);
            byte[] responseMessage = viewResolver.mapView(viewName);

            DataOutputStream dos = new DataOutputStream(out);
            response(dos, responseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Response 메시지를 전송합니다.
     * @param dos
     * @param body
     */
    private void response(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
