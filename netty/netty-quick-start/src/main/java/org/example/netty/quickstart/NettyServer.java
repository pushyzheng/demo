package org.example.netty.quickstart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.quickstart.handler.EchoServerHandler;

@Slf4j
public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // a helper class that sets up a server
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        //ch.pipeline().addLast(new DiscardServerHandler());
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                })
                // for the NioServerSocketChannel that accepts incoming connections
                .option(ChannelOption.SO_BACKLOG, 128)
                // for the Channels accepted by the parent ServerChannel (NioSocketChannel, in this case)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            // Bind and start to accept incoming connections.
            ChannelFuture f = bootstrap.bind(port).sync();

            log.info("Running on localhost:{}", port);
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("fail to set up", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer(8080);
        server.run();
    }
}
