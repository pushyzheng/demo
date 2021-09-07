package org.example.reactor.master;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个 selector 绑定了一个 I/O 线程
 * 但可以管理多个 channel
 */
public class SlaveHandler {

    /**
     * 线程池
     */
    private static final ExecutorService threadPool =
            Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    /**
     * 启动 I/O 线程
     * 开启监听事件以及处理 I/O 事件
     */
    public void startThread(Selector selector) {
        threadPool.submit(() -> {
            try {
                while (selector.select(500) > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            int count = socketChannel.read(buffer);
                            if (count < 0) {
                                socketChannel.close();
                                key.cancel();
                                System.out.println("Read ended: " + socketChannel);
                            } else if (count == 0) {
                                System.out.println("Message size is 0, channel: " + socketChannel);
                            } else {
                                System.out.println("Read message: " + new String(buffer.array()));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
