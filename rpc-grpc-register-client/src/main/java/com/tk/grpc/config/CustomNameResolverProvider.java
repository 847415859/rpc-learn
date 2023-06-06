package com.tk.grpc.config;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class CustomNameResolverProvider extends NameResolverProvider {

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        return new CustomNameResolver(targetUri.toString());
    }

    @Override
    //优先级应该高于DNS（5）命名服务
    protected int priority() {
        return 6;
    }

    @Override
    //名字解析 （注册中心）通信的协议 consul
    public String getDefaultScheme() {
        return "consul";
    }

    @Override
    //作用：告知Grpc 自定义的ResolverProvider生效
    protected boolean isAvailable() {
        return true;
    }
}
