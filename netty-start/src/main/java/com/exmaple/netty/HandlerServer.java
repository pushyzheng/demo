package com.exmaple.netty;

import com.exmaple.handler.EchoChannelHandler;
import com.exmaple.handler.HttpChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Pushy
 * @since 2018/11/9 20:25
 */
public class HandlerServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new HttpServerCodec()); // HTTP解编码器
                        pipeline.addLast(new HttpObjectAggregator(512 * 1024)); // 聚合HTTP消息

                        pipeline.addLast(new EchoChannelHandler());
                        pipeline.addLast(new HttpChannelHandler());


                    }
                });

        ChannelFuture f = b.bind("localhost", 8080).sync();
        f.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

}
