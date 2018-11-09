package com.exmaple.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Pushy
 * @since 2018/11/8 20:16
 */
public class SocketServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                // 创建新的线程处理客户端连接
                new ServerSockThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
