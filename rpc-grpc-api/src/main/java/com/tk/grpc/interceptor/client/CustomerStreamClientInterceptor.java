package com.tk.grpc.interceptor.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 流式拦截器
 * @Date : 2023/06/06 20:15
 * @Auther : tiankun
 */
@Slf4j
public class CustomerStreamClientInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        log.info("CustomerStreamClientInterceptor...");
        // 把自己开发的ClientStreamTracerFactory融入到gRPC体系
        callOptions = callOptions.withStreamTracerFactory(new CustomClientStreamTracerFactory<>());
        return next.newCall(method,callOptions);
    }
}
