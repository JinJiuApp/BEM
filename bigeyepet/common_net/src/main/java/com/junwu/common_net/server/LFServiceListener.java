package com.junwu.common_net.server;

/**
 * Created by Glen on 2016/1/5.
 * LFServiceListener
 */
public interface LFServiceListener<T> {
    void onServiceCallBack(ServiceEvent<T> event);
}
