package com.example.shell.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public class CatPipeHandler extends AbstractPipeHandler {

    public CatPipeHandler(PipedInputStream inputStream, PipedOutputStream outputStream) {
        super(inputStream, outputStream);
    }

    @Override
    protected void handle(BufferedReader reader) {
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                writeToStream(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeOutStream();
    }
}
