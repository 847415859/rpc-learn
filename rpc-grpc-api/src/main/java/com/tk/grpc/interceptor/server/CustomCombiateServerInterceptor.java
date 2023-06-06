package com.tk.grpc.interceptor.server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 集成 请求和响应拦截器
 * @Date : 2023/06/06 19:57
 * @Auther : tiankun
 */
@Slf4j
public class CustomCombiateServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("CustomCombiateServerInterceptor...");
        //1. 包装ServerCall 处理服务端响应拦截
        CustomServerCall<ReqT,RespT> reqTRespTCustomServerCall = new CustomServerCall<>(call);
        //2. 包装Listener   处理服务端请求拦截
        CustomServerCallListener<ReqT> reqTCustomServerCallListener = new CustomServerCallListener<>(next.startCall(reqTRespTCustomServerCall, headers));
        return reqTCustomServerCallListener;
    }
}
