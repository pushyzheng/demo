package com.example;

import com.example.test.HtmlTestHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author Pushy
 * @since 2018/11/7 8:52
 */
public class HtmlHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /*@Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        URL location = HtmlTestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        String path = location.toURI() + "web.html";
        path = !path.contains("file:") ? path : path.substring(5);
        File file = new File(path);

        HttpResponse response = new DefaultHttpResponse(
                request.protocolVersion(), HttpResponseStatus.OK);
        ctx.write(response);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        URL location = HtmlTestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        String path = location.toURI() + "web.html";
        path = !path.contains("file:") ? path : path.substring(5);
        File file = new File(path);

        HttpResponse response = new DefaultHttpResponse(
                request.protocolVersion(), HttpResponseStatus.OK);

        ctx.write(new DefaultHttpResponse(
                request.protocolVersion(), HttpResponseStatus.OK));

        FileInputStream in = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(
                in.getChannel(), 0, file.length());

        ctx.write(region);

        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
