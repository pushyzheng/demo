package com.exmaple.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author Pushy
 * @since 2018/11/8 20:24
 */
public class EchoServer {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received => " + msg.toString(CharsetUtil.UTF_8));
                    }
                });

        ChannelFuture f = b.bind("localhost", 8080).sync();

        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Running on localhost:8080.");
                } else {
                    System.out.println("Bind error.");
                }
            }
        });

        f.channel().closeFuture().sync();

        group.shutdownGracefully();
    }

}
