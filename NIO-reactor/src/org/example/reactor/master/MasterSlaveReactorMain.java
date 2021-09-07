package org.example.reactor.master;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MasterSlaveReactorMain {

    /**
     * 一个主 selector
     */
    private static Selector masterSelector;

    /**
     * 多个从 selector
     */
    private static List<Selector> slaveSelectors;

    public static void main(String[] args) throws Exception {
        // 初始化主 reactor 和 ServerSocketChannel
        masterSelector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(masterSelector, SelectionKey.OP_ACCEPT);

        // 初始化所有的从 reactor
        int coreNum = Runtime.getRuntime().availableProcessors();
        slaveSelectors = new ArrayList<>();
        for (int i = 0; i < 2 * coreNum; i++) {
            AbstractSelector slaveSelector = SelectorProvider.provider().openSelector();
            slaveSelectors.add(slaveSelector);
            new SlaveHandler().startThread(slaveSelector);
        }

        int index = 0;
        while (masterSelector.select() > 0) {
            Set<SelectionKey> keys = masterSelector.selectedKeys();
            for (SelectionKey key : keys) {
                keys.remove(key);
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("Accept request from: " + socketChannel.getRemoteAddress());

                    Selector selector = slaveSelectors.get((index++) % coreNum);
                    // 将当前的 channel 绑定到一个 selector 上
                    // 由一个 IO 线程来完成读事件的监听以及 I/O 操作
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    // 新的 channel 连接与注册, 唤醒 selector
                    selector.wakeup();
                }
            }
        }
    }
}
