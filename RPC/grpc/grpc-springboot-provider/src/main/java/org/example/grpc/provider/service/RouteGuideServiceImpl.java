package org.example.grpc.provider.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.grpc.proto.Feature;
import org.example.grpc.proto.Point;
import org.example.grpc.proto.RouteGuideGrpc;

@GrpcService
public class RouteGuideServiceImpl extends RouteGuideGrpc.RouteGuideImplBase {

    @Override
    public void getFeature(Point request, StreamObserver<Feature> observer) {
        Feature result = Feature.newBuilder()
                .setName("Jeremy")
                .setLocation(request)
                .build();

        observer.onNext(result);
        observer.onCompleted();
    }
}
