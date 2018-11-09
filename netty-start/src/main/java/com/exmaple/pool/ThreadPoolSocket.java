package com.exmaple.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pushy
 * @since 2018/11/8 20:21
 */
public class ThreadPoolSocket {

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            final Socket socket = serverSocket.accept();
            // 让线程池来处理客户端的socket连接逻辑
            threadPool.execute(() -> {
                try {
                    ServerHandler.handlerRequest(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
