package com.tk.grpc.interceptor.client;

import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 拦截器工厂类
 * @Date : 2023/06/06 20:13
 * @Auther : tiankun
 */
@Slf4j
public class CustomClientStreamTracerFactory<ReqT,RespT> extends ClientStreamTracer.Factory {

    @Override
    public ClientStreamTracer newClientStreamTracer(ClientStreamTracer.StreamInfo info, Metadata headers) {
        log.info("CustomClientStreamTracerFactory.newClientStreamTracer");
        return new CustomClientStreamTracer<>();
    }
}
