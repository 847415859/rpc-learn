package com.tk.grpc.interceptor.client;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 简单的服务器拦截
 * @Date : 2023/06/06 19:28
 * @Auther : tiankun
 */
@Slf4j
public class CustomServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("serverCall :{}",serverCall);
        log.info("metadata: {}",metadata);
        log.info("serverCallHandler:{}",serverCallHandler);
        return serverCallHandler.startCall(serverCall,metadata);
    }
}
