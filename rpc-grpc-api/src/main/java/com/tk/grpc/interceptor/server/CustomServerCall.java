package com.tk.grpc.interceptor.server;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 拦截请求
 * @Date : 2023/06/06 19:55
 * @Auther : tiankun
 */
@Slf4j
public class CustomServerCall<ReqT,RespT> extends ForwardingServerCall.SimpleForwardingServerCall<ReqT,RespT> {
    protected CustomServerCall(ServerCall<ReqT, RespT> delegate) {
        super(delegate);
    }

    @Override
    //指定发送消息的数量 【响应消息】
    public void request(int numMessages) {
        log.debug("response 指定消息的数量 【request】");
        super.request(numMessages);
    }

    @Override
    //设置响应头
    public void sendHeaders(Metadata headers) {
        log.debug("response 设置响应头 【sendHeaders】");
        super.sendHeaders(headers);
    }

    @Override
    //响应数据
    public void sendMessage(RespT message) {
        log.debug("response 响应数据  【send Message 】 {} ", message);
        super.sendMessage(message);
    }

    @Override
    //关闭连接
    public void close(Status status, Metadata trailers) {
        log.info("respnse 关闭连接 【close】");
        super.close(status, trailers);
    }



}
