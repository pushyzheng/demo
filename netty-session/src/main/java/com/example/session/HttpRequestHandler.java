package com.example.session;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.util.Set;

/**
 * @author Pushy
 * @since 2019-3-20 10:33:14
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String CLIENT_COOKIE_NAME = "JSESSIONID";

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 构造 FullHttpResponse 对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1 ,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));

        // 如果该客户端该服务端中不存在 Session记录，将会注册新的Session
        // 并通过 set-cookie 的响应头让客户端保存的sessionId
        if (!hasSessionId(request)) {
            HttpSession session = SessionManager.addSession();
            // 创建 Cookie 对象，并通过 ServerCookieEncoder 编码为cookie字符串
            Cookie cookie = new DefaultCookie(CLIENT_COOKIE_NAME, session.getId());
            cookie.setPath("/");
            String cookieStr = ServerCookieEncoder.STRICT.encode(cookie);
            response.headers().set(HttpHeaderNames.SET_COOKIE, cookieStr);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 判断该客户端在服务端中是否有 Session 记录
     */
    private boolean hasSessionId(FullHttpRequest request) {
        // 从客户端请求头中得到cookie字符串
        String cookieStr = request.headers().get("Cookie");
        if (cookieStr != null) {
            // 通过 ServerCookieDecoder 将cookie字符串解码为 Cookie 对象
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieStr);
            for (Cookie cookie : cookies) {
                if (cookie.name().equals(CLIENT_COOKIE_NAME) &&
                        SessionManager.containsSession(cookie.value())) {
                    HttpSession session = SessionManager.getSession(cookie.value());
                    System.out.println(session);
                    return true;
                }
            }
        }
        return false;
    }

}
