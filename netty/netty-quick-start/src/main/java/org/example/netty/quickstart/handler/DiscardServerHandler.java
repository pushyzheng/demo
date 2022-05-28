package org.example.netty.quickstart.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.common.utils.NettyUtils;

@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * this method invoked whenever data is received
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Try.run(() -> logMessage(ctx, (ByteBuf) msg))
                // ByteBuf is a reference-counted object
                // which has to be released explicitly via the release() method
                .andFinally(() -> ReferenceCountUtil.release(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("caught exception, channelId: {}", ctx.channel().id(), cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel is inactive, channelId: {}", ctx.channel().id());
    }

    private void logMessage(ChannelHandlerContext ctx, ByteBuf msg) {
        log.info("received message, channelId: {}, content: {}", ctx.channel().id(),
                NettyUtils.toString(msg));
    }
}
