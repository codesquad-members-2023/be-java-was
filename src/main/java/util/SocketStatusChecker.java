package util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;

public class SocketStatusChecker {
    /**
     * Client가 연결되면 Timer가 실행되고, 5초 후에 BufferReader에서 읽을 byte가 없거나 IOException이 발생하면(소켓이 닫힌 경우라고 생각됩니다.)
     * Timer를 취소하고 socket을 close 시도합니다.
     */
    private static final int PING_INTERVAL = 5000;

    public static void ping(Socket connection, Logger logger) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    connection.getInputStream().available();
                    byte[] buffer = new byte[1024];
                    int read = connection.getInputStream().read(buffer);

                    if (read == -1) {
                        logger.debug("Client IP : {} Port : {} 클라이언트와의 연결이 끊어졌습니다.", connection.getInetAddress(),
                                connection.getPort());
                        connection.close();
                        timer.cancel();
                    }
                } catch (IOException e) {
                    logger.debug("Client IP : {} Port : {} 클라이언트의 응답이 없습니다. 연결을 종료합니다.", connection.getInetAddress(),
                            connection.getPort());
                    try {
                        connection.close();
                    } catch (IOException ignored) {
                    }
                    timer.cancel();
                }
            }
        }, PING_INTERVAL, PING_INTERVAL);
    }
}
