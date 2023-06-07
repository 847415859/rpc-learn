package com.tk.grpc.service;

import com.google.protobuf.ProtocolStringList;
import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date : 2023/06/06 15:24
 * @Auther : tiankun
 */
@Slf4j
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    /**
     *  1. 接受client提交的参数  request.getParameter()
     *  2. 业务处理 service+dao 调用对应的业务功能。
     *  3. 提供返回值
     * @param request
     * @param responseObserver
     */
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        // 1.获取参数
        String name = request.getName();
        // 2.业务处理
        log.info("接受到的参数为：{}",name);
        // 3.封装响应
        HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
        responseBuilder.setResult("Hello " + name);
        HelloProto.HelloResponse helloResponse = responseBuilder.build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void helloRepeat(HelloProto.HelloRepeatRequestt request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        ProtocolStringList nameList = request.getNameList();
        log.info("接受到的参数为：{}",nameList);
        // 3.封装响应
        HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
        responseBuilder.setResult("Hello Repeat");
        HelloProto.HelloResponse helloResponse = responseBuilder.build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }


    /**
     * 服务端流式 RPC
     * @param request
     * @param responseObserver
     */
    @Override
    public void c2ss(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(Long.parseLong(random.nextInt(2000) + ""));
            } catch (InterruptedException e) {
            }
            HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
            responseBuilder.setResult("Result "+i);
            HelloProto.HelloResponse helloResponse = responseBuilder.build();
            responseObserver.onNext(helloResponse);
        }
        responseObserver.onCompleted();
    }


    /**
     * 客户端企业端 双向流
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloProto.HelloRequest> bothWaySend(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        return new StreamObserver<HelloProto.HelloRequest>() {
            @Override
            public void onNext(HelloProto.HelloRequest value) {
                log.info("接受到 client 消息为：{}",value);
                HelloProto.HelloResponse helloResponse = HelloProto.HelloResponse.newBuilder()
                        .setResult("double stream " + value)
                        .build();
                responseObserver.onNext(helloResponse);
            }

            @Override
            public void onError(Throwable t) {
                log.info("接受到 client 消息错误 ：{}",t);
            }

            @Override
            public void onCompleted() {
                log.info("接受到 client 消息完成");
                responseObserver.onCompleted();
            }
        };
    }
}
