package com.tk.grpc.interceptor.client;

import com.tk.grpc.interceptor.server.CustomCallListener;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptors;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;

/**
 * @Description: 这个类型 适用于控制 拦截 请求发送各个环节
 * @Date : 2023/06/06 19:34
 * @Auther : tiankun
 */
@Slf4j
public class CustomForwardingClientClass<ReqT,RespT> extends ClientInterceptors.CheckedForwardingClientCall<ReqT,RespT> {
    protected CustomForwardingClientClass(ClientCall<ReqT, RespT> delegate) {
        super(delegate);
    }

    /**
     * 开启调用，看这个RPC请求是不是可以被发起
     * @param listener
     * @param metadata
     * @throws Exception
     */
    @Override
    protected void checkedStart(Listener<RespT> listener, Metadata metadata) throws Exception {
        /*
             真正的去发起grpc的请求
             是否真正发送grpc的请求，取决这个start方法的调用
             delegate().start(responseListener, headers);
         */
        log.info("CustomForwardingClientClass.checkedStart...:{}",metadata);
        delegate().start(new CustomCallListener<>(listener), metadata);
    }

    /**
     * 发送消息 缓冲区
     * @param message
     */
    @Override
    public void sendMessage(ReqT message) {
        log.info("CustomForwardingClientClass.sendMessage:{}",message);
        super.sendMessage(message);
    }

    /**
     * 指定发送消息的数量
     * @param numMessages
     */
    @Override
    public void request(int numMessages) {
        log.info("CustomForwardingClientClass.request:{}",numMessages);
        super.request(numMessages);
    }

    /**
     * 开启半连接 请求消息无法发送，但是可以接受响应的消息
     */
    @Override
    public void halfClose() {
        log.info("CustomForwardingClientClass.halfClose");
        super.halfClose();
    }

    @Override
    public void cancel(@Nullable String message, @Nullable Throwable cause) {
        log.info("CustomForwardingClientClass.cancel:{}  {}",message,cause);
        super.cancel(message, cause);
    }

    @Override
    public void setMessageCompression(boolean enabled) {
        log.info("CustomForwardingClientClass.setMessageCompression:{}",enabled);
        super.setMessageCompression(enabled);
    }
}
