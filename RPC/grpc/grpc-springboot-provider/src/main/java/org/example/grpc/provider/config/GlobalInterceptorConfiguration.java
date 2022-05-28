package org.example.grpc.provider.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }

    public static class LogGrpcInterceptor implements ServerInterceptor {
        private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
                                                                     ServerCallHandler<ReqT, RespT> serverCallHandler) {
            log.info(serverCall.getMethodDescriptor().getFullMethodName());
            return serverCallHandler.startCall(serverCall, metadata);
        }
    }

}
