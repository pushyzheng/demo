package com.example.shell.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedInputStream;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public class StdoutPipeHandler extends AbstractPipeHandler {

    public StdoutPipeHandler(PipedInputStream inputStream) {
        super(inputStream, null);
    }

    @Override
    protected void handle(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // StdoutPipeHandler 是管道的最后一个单元，不需要调用 closeOutStream 关闭 PipedOutputStream
    }

    @Override
    protected void closeOutStream() {
        throw new UnsupportedOperationException("StdoutPipeHandler have not PipedOutputStream");
    }
}
