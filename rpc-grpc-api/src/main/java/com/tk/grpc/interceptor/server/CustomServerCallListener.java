package com.tk.grpc.interceptor.server;

import io.grpc.ForwardingServerCallListener;
import io.grpc.ServerCall;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 请求数据监听器
 * @Date : 2023/06/06 19:49
 * @Auther : tiankun
 */
@Slf4j
public class CustomServerCallListener<ReqT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
    protected CustomServerCallListener(ServerCall.Listener<ReqT> delegate) {
        super(delegate);
    }

    @Override
    public void onMessage(ReqT message) {
        log.info("CustomServerCallListener.onMessage：{}",message);
        super.onMessage(message);
    }

    @Override
    public void onHalfClose() {
        // 监听到了半连接
        log.info("CustomServerCallListener.onHalfClose");
        super.onHalfClose();
    }

    @Override
    public void onCancel() {
        log.info("CustomServerCallListener.onCancel");
        super.onCancel();
    }

    @Override
    public void onComplete() {
        log.info("CustomServerCallListener.onComplete");
        super.onComplete();
    }

    /**
     * 准备接受请求数据
     */
    @Override
    public void onReady() {
        log.info("CustomServerCallListener.onReady");
        super.onReady();
    }
}
