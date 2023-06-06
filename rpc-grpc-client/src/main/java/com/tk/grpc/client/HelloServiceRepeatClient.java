package com.tk.grpc.client;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Date : 2023/06/06 15:45
 * @Auther : tiankun
 */
@Slf4j
public class HelloServiceRepeatClient {
    public static void main(String[] args) {
        // 1.创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        // 2.获得代理对象
        HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
        // 3.RPC调用
        // 封装request对象
        HelloProto.HelloRepeatRequestt.Builder helloRepeatRequestt = HelloProto.HelloRepeatRequestt.newBuilder();
        helloRepeatRequestt.addName("田坤0");
        helloRepeatRequestt.addName("田坤1");
        helloRepeatRequestt.addName("田坤2");
        HelloProto.HelloRepeatRequestt helloRepeatRequest = helloRepeatRequestt.build();
        // rpc调用
        HelloProto.HelloResponse helloResponse = helloService.helloRepeat(helloRepeatRequest);
        String result = helloResponse.getResult();
        log.info("调用接口返回的值为：{}",result);
    }
}
