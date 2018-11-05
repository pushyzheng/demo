package com.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class WebSocketServer {

    private static final String host = "localhost";
    private static final int port = 8080;

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap b = new ServerBootstrap();
        b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatServerInitializer(channelGroup));

        ChannelFuture f = b.bind(host, port).sync();

        f.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Running on http://" + host + ":" + port);

            } else {
                System.out.println("绑定失败!!!");
            }
        });

        f.channel().closeFuture().sync();

    }

}
