package com.example.shell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Pushy
 * @since 2020/6/7
 */
public class ReadFileThread extends Thread {

    private PipedOutputStream outputStream;

    private List<String> files;

    public ReadFileThread(PipedOutputStream outputStream, List<String> files) {
        this.outputStream = outputStream;
        this.files = files;
    }

    @Override
    public void run() {
        try {
            for (String filename : files) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    outputStream.write(line.getBytes());
                    outputStream.write('\n');
                }
                outputStream.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
