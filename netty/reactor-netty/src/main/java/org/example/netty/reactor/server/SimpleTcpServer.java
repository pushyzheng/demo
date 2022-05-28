package org.example.netty.reactor.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.common.utils.NettyUtils;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpServer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SimpleTcpServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.create()
                .handle((inbound, outbound) -> inbound
                        .receive()
                        .flatMap(r -> handleRequest(r, outbound)));

        tcpServer.warmup().block();

        DisposableServer server = tcpServer
                .host(HOST)
                .port(PORT)
                .bindNow();
        log.info("Running on http://{}:{}", HOST, PORT);
        server.onDispose().block();
    }

    private static NettyOutbound handleRequest(ByteBuf byteBuf, NettyOutbound outbound) {
        log.info("Receive request: {}", NettyUtils.toString(byteBuf));
        return outbound.sendByteArray(Mono.just("Hello World".getBytes(StandardCharsets.UTF_8)));
    }
}
