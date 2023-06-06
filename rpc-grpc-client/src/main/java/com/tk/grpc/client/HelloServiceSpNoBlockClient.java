package com.tk.grpc.client;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 服务端流式RPC 非阻塞客户端
 * @Date : 2023/06/06 17:35
 * @Auther : tiankun
 */
@Slf4j
public class HelloServiceSpNoBlockClient {
    public static void main(String[] args) throws InterruptedException {
        // 1.创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        // 2.获得代理对象
        HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(managedChannel);
        // 3.RPC调用
        // 封装request对象
        HelloProto.HelloRequest.Builder helloRequestBuilder = HelloProto.HelloRequest.newBuilder();
        helloRequestBuilder.setName("参数");

        HelloProto.HelloRequest helloRequest = helloRequestBuilder.build();
        // rpc调用
        helloServiceStub.c2ss(helloRequest, new StreamObserver<HelloProto.HelloResponse>() {
            @Override
            public void onNext(HelloProto.HelloResponse helloResponse) {
                //服务端 响应了 一个消息后，需要立即处理的话。把代码写在这个方法中。
                log.info("服务端每一次响应的信息 {}" , helloResponse.getResult());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                //需要把服务端 响应的所有数据 拿到后，在进行业务处理。
                log.info("服务端响应结束 后续可以根据需要 在这里统一处理服务端响应的所有内容");
            }
        });

        managedChannel.awaitTermination(15, TimeUnit.SECONDS);
    }
}
