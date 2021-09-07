package org.example.reactor.multi;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolHandler {

    /**
     * 池化的 worker
     */
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

    public void doHandle(SelectionKey key) {
        // 提交线程池任务
        // 由线程池来完成阻塞的 I/O 操作
        threadPool.submit(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int count = socketChannel.read(buffer);
            if (count < 0) {
                socketChannel.close();
                key.cancel();
                System.out.println("Read ended: " + socketChannel);
            } else if (count == 0) {
                return null;
            } else {
                System.out.println("Read message: " + new String(buffer.array()));
            }
            return null;
        });
    }
}
