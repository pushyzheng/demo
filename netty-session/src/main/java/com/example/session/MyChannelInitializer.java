package com.example.session;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Pushy
 * @since 2019-3-20 10:33:14
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec()); // HTTP解编码器
        pipeline.addLast(new HttpObjectAggregator(512 * 1024)); // 聚合HTTP消息
        pipeline.addLast(new ChunkedWriteHandler());  // 写文件
        pipeline.addLast(new HttpRequestHandler());
    }
}
