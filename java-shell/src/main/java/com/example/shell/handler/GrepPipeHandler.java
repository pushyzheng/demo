package com.example.shell.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public class GrepPipeHandler extends AbstractPipeHandler {

    private String keyword;

    public GrepPipeHandler(PipedInputStream inputStream, PipedOutputStream outputStream,
                           String keyword) {
        super(inputStream, outputStream);
        this.keyword = keyword;
    }

    @Override
    protected void handle(BufferedReader reader) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.contains(keyword)) {
                    writeToStream(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeOutStream();
    }
}
