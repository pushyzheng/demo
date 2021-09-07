package org.example.reactor.multi;

import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiWorkThreadMain {

    /**
     * 仍然由一个 Selector 来完成所有的 I/O 事件监听
     * <p>
     * 但是会在监听到读事件后,转交给池化的 handler 来处理真正的 I/O 操作
     */
    private static Selector selector;


    public static void main(String[] args) throws Exception {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        registerEvent(serverSocketChannel, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 处理监听事件:
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("Accept request from: " + socketChannel.getRemoteAddress());
                    SelectionKey readKey = registerEvent(socketChannel, SelectionKey.OP_READ);
                    readKey.attach(new ThreadPoolHandler());
                } else if (key.isReadable()) {
                    // 读事件转交给池化的 Handler 来操作
                    ThreadPoolHandler handler = (ThreadPoolHandler) key.attachment();
                    handler.doHandle(key);
                }
            }
        }
    }


    /**
     * 将 Channel 注册到 Selector 上
     */
    public static SelectionKey registerEvent(SelectableChannel channel, int event)
            throws ClosedChannelException {
        return channel.register(selector, event);
    }
}
