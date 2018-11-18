package site.pushy.protobuf.bootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import site.pushy.protobuf.initializer.ServerChannelInitializer;

public class ServerBootstrap {

    private static final String host = "localhost";
    private static final int port = 8081;

    public static void main(String[] args) throws InterruptedException {

        io.netty.bootstrap.ServerBootstrap b = new io.netty.bootstrap.ServerBootstrap();
        b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());

        ChannelFuture f = b.bind(host, port).sync();

        f.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Running on " + host + ":" + port);
            } else {
                System.out.println("绑定失败!!!");
            }
        });

        f.channel().closeFuture().sync();

    }

}
