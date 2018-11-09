package com.exmaple.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author Pushy
 * @since 2018/11/8 20:19
 */
public class ServerSockThread extends Thread {

    private Socket socket;

    public ServerSockThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(buf.read());

            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
