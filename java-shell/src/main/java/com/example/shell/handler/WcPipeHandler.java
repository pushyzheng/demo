package com.example.shell.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public class WcPipeHandler extends AbstractPipeHandler {

    public WcPipeHandler(PipedInputStream inputStream, PipedOutputStream outputStream) {
        super(inputStream, outputStream);
    }

    @Override
    protected void handle(BufferedReader reader) {
        int cnt = 0;
        try {
            while (reader.readLine() != null) {
                cnt++;
            }
            writeToStream(String.valueOf(cnt));
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeOutStream();
    }
}
