package com.exmaple.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Pushy
 * @since 2018/11/8 20:24
 */
@Slf4j
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
        f.addListener(future -> {
            if (future.isSuccess()) {
                log.info("Running on localhost:8080.");
            } else {
                log.info("Bind error.");
            }
        });
        f.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

}
