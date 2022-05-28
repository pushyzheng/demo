package org.example.netty.quickstart.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.common.utils.NettyUtils;

@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Try.of(() -> echoMessage(ctx, (ByteBuf) msg))
                .onSuccess(resp -> log.info("Echo message: {}", resp));
        // Netty releases it for you when it is written out to the wire
        // So don't call method to release ByteBuf
    }

    private String echoMessage(ChannelHandlerContext ctx, ByteBuf msg) {
        String response = NettyUtils.toString(msg) + "!";
        // writeAndFlush = write + flush
        ctx.writeAndFlush(NettyUtils.toByteBuf(response));
        return response;
    }
}
