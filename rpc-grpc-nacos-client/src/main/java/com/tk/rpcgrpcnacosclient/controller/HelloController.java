package com.tk.rpcgrpcnacosclient.controller;

import com.tk.HelloProto;
import com.tk.HelloServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date : 2023/06/07 18:57
 * @Auther : tiankun
 */
@RestController
@Slf4j
public class HelloController {

    @GrpcClient("rpc-grpc-nacos-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    @GetMapping("say")
    public Object say(){
        HelloProto.HelloResponse helloResponse = helloServiceStub.hello(HelloProto.HelloRequest
                .newBuilder()
                .setName("田坤-nacos")
                .build());
        log.info("response :{}",helloResponse.getResult());
        return helloResponse.getResult();
    }
}
