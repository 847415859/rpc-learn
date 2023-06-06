package com.tk.grpc.interceptor.server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 复杂的服务端拦截器
 * @Date : 2023/06/06 19:48
 * @Auther : tiankun
 */
@Slf4j
public class CustomComplexServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("CustomComplexServerInterceptor...");
        // 默认返回的ServerCall.Listener仅仅能够完成请求数据的监听，单没有拦截功能
        //所以要做扩展，采用包装器设计模式。 return next.startCall(call, headers);
        return new CustomServerCallListener<>(next.startCall(call, headers));
    }
}
