package com.example;

import com.example.test.HtmlTestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Pushy
 * @since 2018/11/6 10:39
 */
public class HttpServer {

    private static final String host = "localhost";
    private static final int port = 8080;

    public static void main(String[] args) {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec()); // HTTP解编码器
                            pipeline.addLast(new HttpObjectAggregator(512 * 1024)); // 聚合HTTP消息
                            pipeline.addLast(new ChunkedWriteHandler());  // 写文件
                            pipeline.addLast(new HttpServerHandler());
//                            pipeline.addLast(new HtmlHandler());
                            pipeline.addLast(new HtmlTestHandler());
                            //pipeline.addLast(new TestHandler());
                        }
                    });
            ChannelFuture f = b.bind(host, port).sync();
            f.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("Running on http://" + host + ":" + port);
                } else {
                    System.out.println("绑定失败!!!");
                }
            });
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
