package com.exmaple.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Pushy
 * @since 2018/11/9 19:30
 */
public class EchoChannelHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("EchoChannelHandler received => " + msg);
        ctx.fireChannelRead(msg);
    }
}
