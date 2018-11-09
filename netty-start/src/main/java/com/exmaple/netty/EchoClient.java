package com.exmaple.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author Pushy
 * @since 2018/11/9 13:39
 */
public class EchoClient {

    private static final String host = "localhost";
    private static final int port = 8080;

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 在连接成功后向客户端发送消息
                            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8));
                        }

                        @Override
                        protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Got server response => " + msg.toString(CharsetUtil.UTF_8));
                        }
                    });

            ChannelFuture f = b.connect(host, port);  // 连接到远程节点

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("连接成功！");
                    } else {
                        System.out.println("连接失败！");
                    }
                }
            });

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }

    }

}
