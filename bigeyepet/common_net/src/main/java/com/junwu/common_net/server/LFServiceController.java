package com.junwu.common_net.server;


import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.jaryjun.common_base.user.LFApplication;
import com.junwu.common_net.R;
import com.junwu.common_net.base.LFBaseRequest;
import com.junwu.pet_net.NetworkManager;
import com.junwu.pet_net.RequestManager;

/**
 * 中间层，解耦Framework发服务方法
 * Created by youj on 2015/5/6.
 */
public class LFServiceController {

    public static <T> void sendService(LFBaseRequest request, Class<T> responseClass, ServiceCacheControl cacheControl, final LFServiceListener<T> listener) {

        if (cacheControl == null) {
            //未开启缓存的情况下
            NetworkManager.getInstance(LFApplication.getInstance()).loadData(request.getUrl(), request.getToken(), false, null, 0, 0,
                    request, responseClass, new Response.Listener<T>() {
                        @Override
                        public void onResponse(T response) {
                            listener.onServiceCallBack(new ServiceEvent(response));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onServiceCallBack(new ServiceEvent(getServiceError(error)));
                        }
                    }, null);
        } else {
            //开启缓存
            NetworkManager.getInstance(LFApplication.getInstance()).loadData(request.getUrl(), request.getToken(),
                    true, cacheControl.getCacheKey(), cacheControl.getCacheDuration(), cacheControl.getRefreshDuration(),
                    request, responseClass, new Response.Listener<T>() {
                        @Override
                        public void onResponse(T response) {
                            listener.onServiceCallBack(new ServiceEvent(response));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onServiceCallBack(new ServiceEvent(getServiceError(error)));
                        }
                    }, null);
        }
    }

    public static void clearCache(String key) {
        RequestManager.getInstance(LFApplication.getInstance()).clearRequestCache(key);
    }

    private static LFServiceError getServiceError(VolleyError volleyError) {
        LFServiceError serviceError = new LFServiceError();

        EServiceErrorType errorType;
        String errorMsg;
        if (volleyError instanceof NoConnectionError) {
            errorType = EServiceErrorType.ERROR_NOT_CONNECTED;
            errorMsg = LFApplication.getInstance().getResources().getString(R.string.service_error_not_connected);
        } else if (volleyError instanceof TimeoutError) {
            errorType = EServiceErrorType.ERROR_TIMEOUT;
            errorMsg = LFApplication.getInstance().getResources().getString(R.string.service_error_common);
        } else {
            errorType = EServiceErrorType.ERROR_NONE;
            errorMsg = LFApplication.getInstance().getResources().getString(R.string.service_error_common);
        }
        serviceError.setErrorType(errorType);
        serviceError.setErrorMsg(errorMsg);
        return serviceError;
    }

}
