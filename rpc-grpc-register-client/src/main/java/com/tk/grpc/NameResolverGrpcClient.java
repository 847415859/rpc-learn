package com.tk.grpc;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import com.tk.grpc.config.CustomNameResolverProvider;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;

import java.util.concurrent.TimeUnit;

public class NameResolverGrpcClient {
    public static void main(String[] args) throws InterruptedException {
        //1 引入新的命名解析
        NameResolverRegistry.getDefaultRegistry().register(new CustomNameResolverProvider());

        //2客户端的开发
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("grpc-server")
                .usePlaintext()
                //3 引入负载均衡算法 round_robin轮训 负载均衡
                .defaultLoadBalancingPolicy("round_robin")
                .build();


        //3 获得stub对象进行调用
        //模拟发送4次请求
        for (int i = 0; i < 4; i++) {
            sendRequest(managedChannel, i);
            Thread.sleep(1000);
        }


        managedChannel.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void sendRequest(ManagedChannel managedChannel, int idx) {
        HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);
        HelloProto.HelloResponse helloRespnose = helloServiceBlockingStub.hello(HelloProto.HelloRequest.newBuilder().setName("xiaohei " + idx).build());
        System.out.println("helloRespnose.getResult() = " + helloRespnose.getResult());

    }
}