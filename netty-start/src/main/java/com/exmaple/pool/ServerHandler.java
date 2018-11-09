package com.exmaple.pool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author Pushy
 * @since 2018/11/8 20:23
 */
public class ServerHandler {

    public static void handlerRequest(Socket client) throws IOException, InterruptedException {
        System.out.println("Handle the client => " + client.getRemoteSocketAddress());

        OutputStream os = client.getOutputStream();
        PrintStream out = new PrintStream(os);

        /* 模拟耗时的IO操作 */
        Thread.sleep(2000);

        System.out.println("Send data to client");

        out.println("Hello World");
        out.flush();
    }

}

