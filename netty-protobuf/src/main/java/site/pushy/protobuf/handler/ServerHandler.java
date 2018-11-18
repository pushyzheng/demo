package site.pushy.protobuf.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import site.pushy.protobuf.PersonEntity;

/**
 * @author Pushy
 * @since 2018/11/17 19:51
 */
public class ServerHandler extends SimpleChannelInboundHandler<PersonEntity.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonEntity.Person person) throws Exception {
        System.out.println("chanelRead0 =>" + person.getName() );
    }
}
