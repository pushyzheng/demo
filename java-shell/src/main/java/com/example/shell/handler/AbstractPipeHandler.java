package com.example.shell.handler;

import java.io.*;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public abstract class AbstractPipeHandler implements Runnable {

    private PipedInputStream inputStream;

    private PipedOutputStream outputStream;

    public AbstractPipeHandler(PipedInputStream inputStream, PipedOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     * 每一个命令的具体处理逻辑
     */
    protected abstract void handle(BufferedReader reader);

    /**
     * 写入到 PipedInputStream 当中，以供下一个命令元素读取数据
     */
    protected void writeToStream(String out) {
        if (out.length() == 0) return;

        try {
            outputStream.write(out.getBytes());
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在完成所有数据处理之后，关闭 PipedOutputStream 输出流
     */
    protected void closeOutStream() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每个命令所属线程启动执行的逻辑
     */
    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        handle(reader);
    }

    @Override
    public String toString() {
        return "AbstractPipeHandler(inputStream=" + inputStream + ", outputStream=" + outputStream + ")";
    }
}
