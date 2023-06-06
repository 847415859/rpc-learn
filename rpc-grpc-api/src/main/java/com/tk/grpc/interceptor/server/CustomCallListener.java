package com.tk.grpc.interceptor.server;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用于监听响应，并对响应进行拦截
 * @Date : 2023/06/06 19:36
 * @Auther : tiankun
 */
@Slf4j
public class CustomCallListener<RespT> extends ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> {

    public CustomCallListener(ClientCall.Listener<RespT> delegate) {
        super(delegate);
    }

    @Override
    public void onMessage(RespT message) {
        log.info("CustomCallListener.onMessage:{}",message);
        super.onMessage(message);
    }

    @Override
    public void onHeaders(Metadata headers) {
        log.info("CustomCallListener.onHeaders:{}",headers);
        super.onHeaders(headers);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        log.info("CustomCallListener.onClose:{}, {}",status,trailers);
        super.onClose(status, trailers);
    }

    @Override
    public void onReady() {
        log.info("CustomCallListener.onReady");
        super.onReady();
    }
}
