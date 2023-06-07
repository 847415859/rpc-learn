package com.tk.grpc.client;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import com.tk.grpc.interceptor.client.CustomerStreamClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 双向流
 * @Date : 2023/06/07 11:21
 * @Auther : tiankun
 */
@Slf4j
public class HelloDoubleStreamServiceClient {
    public static void main(String[] args) throws InterruptedException {
        // 1.创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .intercept(new CustomerStreamClientInterceptor())
                .build();
        // 2.获得代理对象
        HelloServiceGrpc.HelloServiceStub helloService = HelloServiceGrpc.newStub(managedChannel);
        // 3.RPC调用
        // 封装request对象
        HelloProto.HelloRequest.Builder helloRequestBuilder = HelloProto.HelloRequest.newBuilder();
        StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloService.bothWaySend(new StreamObserver<HelloProto.HelloResponse>() {
            @Override
            public void onNext(HelloProto.HelloResponse value) {
                log.info("接受到 server 信息为：{}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.info("接受到 server 信息错误 ：{}", t);
            }

            @Override
            public void onCompleted() {
                log.info("接受到 server 信息完成");
            }
        });
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(random.nextInt(1000));
            helloRequestStreamObserver.onNext(HelloProto.HelloRequest.newBuilder().setName("client 主动发送消息" + i).build());
        }

        managedChannel.awaitTermination(20, TimeUnit.SECONDS);
    }
}
