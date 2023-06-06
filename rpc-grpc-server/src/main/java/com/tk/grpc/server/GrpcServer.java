package com.tk.grpc.server;

import com.tk.grpc.interceptor.server.CustomCombiateServerInterceptor;
import com.tk.grpc.interceptor.server.CustomComplexServerInterceptor;
import com.tk.grpc.interceptor.client.CustomServerInterceptor;
import com.tk.grpc.interceptor.server.CustomServerStreamFactory;
import com.tk.grpc.service.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @Description:
 * @Date : 2023/06/06 15:36
 * @Auther : tiankun
 */
public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. 绑定端口
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000);
        // 2. 发布服务
        serverBuilder.addService(new HelloServiceImpl());
        serverBuilder.intercept(new CustomServerInterceptor());
        serverBuilder.intercept(new CustomComplexServerInterceptor());
        serverBuilder.intercept(new CustomCombiateServerInterceptor());
        serverBuilder.addStreamTracerFactory(new CustomServerStreamFactory());
        // 3. 创建服务对象
        Server server = serverBuilder.build();
        server.start();
        server.awaitTermination();

    }
}
