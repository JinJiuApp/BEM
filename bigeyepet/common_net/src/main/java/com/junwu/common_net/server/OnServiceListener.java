package com.junwu.common_net.server;

/**
 * service回调接口
 * 除非页面需要处理特殊错误逻辑(框架会处理通用错误逻辑)，否则可仅实现success方法
 * Created by youj on 2015/5/6.
 */
public abstract class OnServiceListener<T> {

    public abstract void onServiceSuccess(T response, String token);

    public void onServiceFail(LFServiceError error, String token) {

    }
}