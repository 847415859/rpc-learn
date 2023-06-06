package com.tk.grpc.interceptor.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 复杂的客户端拦截
 * @Date : 2023/06/06 19:43
 * @Auther : tiankun
 */
@Slf4j
public class CustomComplexClientInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        log.debug("CustomComplexClientInterceptor...");
        /*
             如果我们需要用复杂客户端拦截器 ，就需要对原始的ClientCall进行包装,那么这个时候，就不能返回原始ClientCall对象，
            应该返回 包装的ClientCall ---> CustomForwardingClientClass
         */
        return new CustomForwardingClientClass<>(next.newCall(method,callOptions));
    }
}
