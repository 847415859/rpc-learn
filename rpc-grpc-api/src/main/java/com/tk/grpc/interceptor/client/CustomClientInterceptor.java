package com.tk.grpc.interceptor.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 简单的客户端拦截
 * @Date : 2023/06/06 19:22
 * @Auther : tiankun
 */
@Slf4j
public class CustomClientInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        log.info("CustomClientInterceptor...");
        log.info("methodDescriptor :{}",methodDescriptor);
        log.info("callOptions: {}",callOptions);
        log.info("channel:{}",channel);
        return channel.newCall(methodDescriptor,callOptions);
    }
}
