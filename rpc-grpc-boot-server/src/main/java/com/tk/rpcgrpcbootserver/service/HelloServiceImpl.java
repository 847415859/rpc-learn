package com.tk.rpcgrpcbootserver.service;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @Description:
 * @Date : 2023/06/06 19:02
 * @Auther : tiankun
 */
@GrpcService
@Slf4j
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        log.info("获取到的参数为：{}",name);
        responseObserver.onNext(HelloProto.HelloResponse.newBuilder().setResult("Hello " + name).build());
        responseObserver.onCompleted();
    }
}
