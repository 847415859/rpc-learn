package com.tk.grpc.client;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import com.tk.grpc.interceptor.client.CustomerStreamClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

/**
 * @Description:  服务端流式RPC 阻塞客户端
 * @Date : 2023/06/06 16:12
 * @Auther : tiankun
 */
@Slf4j
public class HelloServiceSpBlockClient {
    public static void main(String[] args) {
        // 1.创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .intercept(new CustomerStreamClientInterceptor())
                .build();
        // 2.获得代理对象
        HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
        // 3.RPC调用
        // 封装request对象
        HelloProto.HelloRequest.Builder helloRequestBuilder = HelloProto.HelloRequest.newBuilder();
        helloRequestBuilder.setName("参数");

        HelloProto.HelloRequest helloRequest = helloRequestBuilder.build();
        // rpc调用
        Iterator<HelloProto.HelloResponse> responseIterator = helloService.c2ss(helloRequest);
        while (responseIterator.hasNext()) {
            HelloProto.HelloResponse helloResponse = responseIterator.next();
            log.info("调用接口返回的值为：{}",helloResponse.getResult());
        }
    }
}
