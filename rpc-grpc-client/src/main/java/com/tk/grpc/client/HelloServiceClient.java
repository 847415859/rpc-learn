package com.tk.grpc.client;

import com.alibaba.fastjson2.JSON;
import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import com.tk.grpc.interceptor.client.CustomClientInterceptor;
import com.tk.grpc.interceptor.client.CustomComplexClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Date : 2023/06/06 15:29
 * @Auther : tiankun
 */
@Slf4j
public class HelloServiceClient{
    public static void main(String[] args) {
        // 1.创建通信的管道
        Map<String, String> serviceConfig = new LinkedHashMap<>();
        LinkedHashMap<String, String> linkedHashMap = JSON.parseObject("{\n" +
                "\t\"methodConfig\": [{\n" +
                "\t\t\"name\": [{\n" +
                "\t\t\t\"service\": \"com.suns.HelloService\",\n" +
                "\t\t\t\"method\": \"hello\"\n" +
                "\t\t}],\n" +
                "\t\t\"retryPolicy\": {\n" +
                "\t\t\t\"maxAttempts\": 3,\n" +
                "\t\t\t\"initialBackoff\": \"0.5s\",\n" +
                "\t\t\t\"maxBackof\": \"30s\",\n" +
                "\t\t\t\"backoffMultiplier\": 2,\n" +
                "\t\t\t\"retryableStatusCodes\": [\n" +
                "\t\t\t\t\"UNAVAILABLE\"\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t}]\n" +
                "}", LinkedHashMap.class);
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .intercept(new CustomClientInterceptor())
                .intercept(new CustomComplexClientInterceptor())
                .defaultServiceConfig(serviceConfig)    // 设置重试参数
                .enableRetry()      // 开启重试
                .build();
        // 2.获得代理对象
        HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
        // 3.RPC调用
        // 封装request对象
        HelloProto.HelloRequest.Builder requestBuilder = HelloProto.HelloRequest.newBuilder();
        requestBuilder.setName("田坤");
        HelloProto.HelloRequest helloRequest = requestBuilder.build();
        // rpc调用
        HelloProto.HelloResponse helloResponse = helloService.hello(helloRequest);
        String result = helloResponse.getResult();
        log.info("调用接口返回的值为：{}",result);
    }
}
