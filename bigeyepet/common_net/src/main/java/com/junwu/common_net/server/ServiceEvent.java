package com.junwu.common_net.server;

/**
 * Created by youj on 2015/5/6.
 */
public class ServiceEvent<T> {
    private T response;
    private LFServiceError error;
    private boolean isSuccessCallback;

    public ServiceEvent(T response) {
        this.isSuccessCallback = true;
        this.response = response;
    }

    public ServiceEvent(LFServiceError error) {
        this.isSuccessCallback = false;
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public boolean isSuccessCallback() {
        return isSuccessCallback;
    }

    public LFServiceError getError() {
        return error;
    }

}
