package com.example.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.*;

/**
 * @author Pushy
 * @since 2018/11/4 21:15
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 握手成功，从该ChannelHandler中移除HttpRequestHandler，因此将不会接受任何HTTP消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
            //group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            group.add(ctx.channel());

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 广播发送
        // group.writeAndFlush(msg.retain());

        // 点对点发送
        ChannelId id = ctx.channel().id(); // 获取当前连接客户端的ChannelId
        group.find(id).writeAndFlush(new TextWebSocketFrame("Hello I'm webSocket server"));

        System.out.println("Got message => " + msg.text());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        WebSocketFrame frame = (WebSocketFrame) msg;

        System.out.println("channelRead::" + frame);

        if (frame instanceof PongWebSocketFrame) {
            System.out.println("Server received client PongWebSocketFrame.");
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
