package org.example.netty.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NettyUtils {

    public String toString(ByteBuf byteBuf) {
        return byteBuf.toString(CharsetUtil.UTF_8);
    }

    public ByteBuf toByteBuf(String s) {
        return Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
    }
}
