package com.exmaple.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

/**
 * @author Pushy
 * @since 2018/11/9 19:39
 */
public class HttpChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("HttpChannelHandler Request =>" + msg.uri() + msg.method());

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));

    }
}
