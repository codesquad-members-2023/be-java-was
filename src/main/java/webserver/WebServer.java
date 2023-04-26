package webserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import configure.AppConfiguration;
import configure.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private static final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";


    public static void main(String args[]) throws Exception {
        AppConfiguration appConfigure = new AppConfiguration();
        ApplicationContext applicationContext = new ApplicationContext(appConfigure);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            executeSqlFile("src/schema.sql", connection);
            executeSqlFile("src/data.sql", connection);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, applicationContext));
                thread.start();
            }
        }
    }

    private static void executeSqlFile(String filePath, Connection connection) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }

                sb.append(line);

                if (line.endsWith(";")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sb.toString());
                    } catch (SQLException e) {
                        System.err.println("Error executing SQL statement: " + sb.toString());
                        e.printStackTrace();
                    }

                    sb.setLength(0);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }

}
