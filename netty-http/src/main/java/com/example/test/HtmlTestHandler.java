package com.example.test;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.*;
import java.net.URL;

/**
 * @author Pushy
 * @since 2018/11/6 21:47
 */
public class HtmlTestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        URL location = HtmlTestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        String path = location.toURI() + "index.html";
        path = !path.contains("file:") ? path : path.substring(5);

        HttpResponse response = new DefaultHttpResponse(
                request.protocolVersion(), HttpResponseStatus.OK);
        ctx.write(response);

        File file = new File(path);
        InputStream in;
        try {
            in = new FileInputStream(file);

            StringBuilder sb = new StringBuilder();

            byte data;
            while ((data = (byte) in.read()) != -1) {
                byte[] bytes = new byte[]{data};
                String s = new String(bytes);
                sb.append(s);
            }
            in.close();

            ctx.write(sb.toString());
            System.out.println("Read all data");
            System.out.println(sb.toString());
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            System.out.println("Future Success");
                        }
                    }).addListener(ChannelFutureListener.CLOSE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
