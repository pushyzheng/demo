package site.pushy.protobuf.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import site.pushy.protobuf.PersonEntity;
import site.pushy.protobuf.handler.ServerHandler;

/**
 * @author Pushy
 * @since 2018/11/18 19:49
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(PersonEntity.Person.getDefaultInstance()));
        pipeline.addLast(new ServerHandler());
    }
}
