package site.pushy.protobuf.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import site.pushy.protobuf.PersonEntity;
import site.pushy.protobuf.handler.ClientHandler;
import site.pushy.protobuf.initializer.ClientChannelInitializer;

/**
 * @author Pushy
 * @since 2018/11/17 19:47
 */
public class ClientBootstrap {

    private static final String host = "localhost";
    private static final int port = 8081;

    public static void main(String[] args) throws InterruptedException {

        Bootstrap b = new Bootstrap();
        b.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        ChannelFuture f = b.connect(host, port)
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功!");
                    }
                });
        f.channel().closeFuture().sync();
    }

}
