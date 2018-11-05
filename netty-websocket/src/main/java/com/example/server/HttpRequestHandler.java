package com.example.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Pushy
 * @since 2018/11/4 20:36
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;

    public HttpRequestHandler(String url) {
        this.wsUri = url;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.out.println("Request " + request.uri() + "【"+ request.method() +"】");

        // 如果请求的URI是/ws，则进行WebSocket 协议升级
        // 并增加引用计数，将它传递给下一个 ChannelHandler进行升级握手
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            String data = "Welcome to WebSocket server";
            HttpResponse response = new DefaultHttpResponse(
                    request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set("Content-Type", "text/html; charset=UTF-8");
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (keepAlive) { // 如果请求了keep-alive，则添加所需要的 HTTP 头信息
                response.headers().set("Content-Length", data.length());
                response.headers().set("Connection", "keep-alive");
            }
            ctx.write(response);  // 将 HttpResponse 写到客户端
            ctx.write(Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
            ChannelFuture future = ctx.writeAndFlush( // 写 LastHttpContent 并冲刷至客户端
                    LastHttpContent.EMPTY_LAST_CONTENT);
            // 如果没有请求keep-alive，则在写操作完成后关闭 Channel
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    /**
     * 处理100 Continue请求以符合HTTP1.1规范
     */
    private void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
