package org.example.reactor.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * 单线程 Reactor 模型
 * <p>
 * 所有的 I/O 操作都在主线程完成
 */
public class SingleReactorMain {

    /**
     * 完成所有的 I/O 事件(连接/可读/可写)的监听
     */
    private static Selector selector;

    public static void main(String[] args) throws Exception {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 注册连接事件
        registerEvent(serverSocketChannel, SelectionKey.OP_ACCEPT);

        // 单线程(主线程)处理监听 I/O 事件
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleEvent(key);
                keys.remove(key);
            }
        }
    }

    /**
     * Handler 角色: 处理连接/读/写操作
     */
    private static void handleEvent(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = acceptServerSocketChannel.accept();
            socketChannel.configureBlocking(false);
            System.out.println("Accept request from: " + socketChannel.getRemoteAddress());
            // 注册读事件
            registerEvent(socketChannel, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            System.out.println("Received message: " + new String(buffer.array()));
        }
    }


    /**
     * 将 Channel 注册到 Selector 上
     */
    public static void registerEvent(SelectableChannel channel, int event)
            throws ClosedChannelException {
        channel.register(selector, event);
    }
}
