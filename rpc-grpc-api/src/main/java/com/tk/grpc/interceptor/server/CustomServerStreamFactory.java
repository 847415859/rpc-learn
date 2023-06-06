package com.tk.grpc.interceptor.server;

import io.grpc.Metadata;
import io.grpc.ServerStreamTracer;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Date : 2023/06/06 20:23
 * @Auther : tiankun
 */
@Slf4j
public class CustomServerStreamFactory extends ServerStreamTracer.Factory {
    @Override
    public ServerStreamTracer newServerStreamTracer(String fullMethodName, Metadata headers) {
        return new CustomServerStreamTracer();
    }
}
