package com.tk.grpc;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.tk.grpc.server.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

/**
 * @Description: Grpc中Consul注册中心的开发
 * @Date : 2023/06/06 20:56
 * @Auther : tiankun
 */
@Slf4j
public class GrpcServerConsul {
    public static void main(String[] args) throws InterruptedException, IOException {
        Random random = new Random();
        int port = new Random().nextInt(65536-1024) + 1024;
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        serverBuilder.addService(new HelloServiceImpl());
        Server server = serverBuilder.build();

        server.start();
        log.debug("server is starting......");
        registerToConsul("127.0.0.1", port);
        server.awaitTermination();
    }

    private static void registerToConsul(String address, int port) {
        Consul consul = Consul.builder().build();
        AgentClient client = consul.agentClient();

        String serviceId = "Server-" + UUID.randomUUID().toString();

        ImmutableRegistration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name("grpc-server")
                .address(address)
                .port(port)
                .tags(Collections.singletonList("server"))
                .meta(Collections.singletonMap("version", "1.0"))
                .check(Registration.RegCheck.tcp(address + ":" + port, 10))
                .build();

        client.register(service);

    }
}
