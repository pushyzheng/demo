package org.example.grpc.consumer.controller;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.grpc.consumer.model.FeatureDTO;
import org.example.grpc.proto.Feature;
import org.example.grpc.proto.Point;
import org.example.grpc.proto.RouteGuideGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private static final Logger log = LoggerFactory.getLogger(RouteController.class);

    @GrpcClient("local-grpc-server")
    private RouteGuideGrpc.RouteGuideStub routeGuideStub;

    @RequestMapping("")
    public DeferredResult<FeatureDTO> get(Point point) {
        long start = System.currentTimeMillis();
        DeferredResult<FeatureDTO> deferredResult = new DeferredResult<>();

        routeGuideStub.getFeature(point, new StreamObserver<Feature>() {
            @Override
            public void onNext(Feature feature) {
                deferredResult.setResult(new FeatureDTO(feature.getName()));
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("onError, point: {}", point, throwable);
                deferredResult.setErrorResult(throwable);
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted, point: {}, cost: {}ms", point, System.currentTimeMillis() - start);
            }
        });
        return deferredResult;
    }
}
