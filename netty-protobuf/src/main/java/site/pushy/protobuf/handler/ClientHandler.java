package site.pushy.protobuf.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import site.pushy.protobuf.PersonEntity;

/**
 * @author Pushy
 * @since 2018/11/17 19:54
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(getPerson());
    }

    private PersonEntity.Person getPerson() {
        return PersonEntity.Person.newBuilder()
                .setName("Pushy")
                .setEmail("1437876073@qq.com")
                .build();
    }

}
