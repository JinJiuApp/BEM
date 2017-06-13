package com.junwu.common_net.server;

import android.text.TextUtils;

import com.jaryjun.common_base.config.LFAppConfig;
import com.jaryjun.common_base.ops.LFSystemOps;
import com.jaryjun.common_base.user.LFApplication;
import com.jaryjun.common_base.utils.NetUtil;
import com.junwu.common_net.R;
import com.junwu.common_net.base.IUi;
import com.junwu.common_net.base.LFBaseRequest;
import com.junwu.common_net.base.LFBaseResponse;
import com.junwu.common_net.base.LFBaseServiceFragment;
import com.junwu.pet_net.RequestManager;

import java.util.List;


/**
 * 封装当前服务相关的信息，发送请求，处理服务回调(解耦presenter和framework)
 * Created by youj on 2015/5/6.
 */
public class LFServiceTask<T> implements LFServiceListener<T> {
    private static final int TOKEN_INVALID_STATUS_CODE = -1;

    private OnServiceListener<T> serviceListener;

    private List<ViewServiceListener> viewServiceListeners;

    private String token;

    private boolean isQuietService;

    private ServiceCacheControl cacheControl;

    private boolean isProcessServiceError = true;//是否要进行统一的服务错误处理, 默认为true

    private LFServiceErrorMockModel mockModel;//mock 服务错误Model

    public LFServiceTask(OnServiceListener<T> serviceListener, List<ViewServiceListener> viewServiceListeners, LFServiceErrorMockModel mockModel, boolean isProcessServiceError) {
        this.serviceListener = serviceListener;
        this.viewServiceListeners = viewServiceListeners;
        this.mockModel = mockModel;
        this.isProcessServiceError = isProcessServiceError;
        isQuietService = isQuietService();
    }

    public void sendService(LFBaseRequest request, Class<T> responseClass, ServiceCacheControl cacheControl, IUi iui) {
        this.token = request.getToken();
        this.cacheControl = cacheControl;

        if (!NetUtil.checkNetwork() && !isQuietService) {
            ServiceEvent serviceEvent = new ServiceEvent(new LFServiceError(EServiceErrorType.ERROR_NOT_CONNECTED, "当前网络未连接,请稍后重试"));
            processServiceEvent(serviceEvent);
            return;
        }

        //cache token
        if (iui instanceof LFBaseServiceFragment) {
            LFBaseServiceFragment fragment = (LFBaseServiceFragment) iui;
            fragment.addToken(token);
            fragment.addTask(this);
        }

        LFServiceController.sendService(request, responseClass, cacheControl, this);
    }

    @Override
    public void onServiceCallBack(ServiceEvent<T> serviceEvent) {
        if (!isQuietService) {
            isQuietService = isQuietService();
        }
        if (isQuietService) return;

        if (null != mockModel && !LFAppConfig.isProduction()) {
            serviceEvent = generateMockServiceEvent();
        }

        processServiceEvent(serviceEvent);
    }

    private void processServiceEvent(ServiceEvent<T> serviceEvent) {
        if (null == serviceEvent) return;

        //判断token是否失效
        ServiceEvent newEvent = getTokenInvalidEvent(serviceEvent);
        if (newEvent != null) {
            serviceEvent = newEvent;
        }

        //process success show
        if (null != viewServiceListeners && !viewServiceListeners.isEmpty()) {
            for (ViewServiceListener viewServiceListener : viewServiceListeners) {
                if (null != viewServiceListener)
                    viewServiceListener.processView(!serviceEvent.isSuccessCallback(), serviceEvent.getError(), isProcessServiceError);
            }
        }

        if (serviceEvent.isSuccessCallback()) {
            //success callback
            if (null != serviceListener) {
                serviceListener.onServiceSuccess(serviceEvent.getResponse(), token);
            }
        } else {
            //error callback
            if (null != serviceListener) {
                serviceListener.onServiceFail(serviceEvent.getError(), token);
            }
        }

        //处理单点登录
        if (newEvent != null) {
            LFSystemOps.handleTokenInvalid(newEvent.getError().getErrorMsg());
        }
    }

    private <T> ServiceEvent getTokenInvalidEvent(ServiceEvent<T> serviceEvent) {
        T response = serviceEvent.getResponse();
        if (response != null && response instanceof LFBaseResponse) {
            if (((LFBaseResponse) response).getStatus() == TOKEN_INVALID_STATUS_CODE) {
                if (cacheControl != null) {
                    LFServiceController.clearCache(cacheControl.getCacheKey());
                }
                return new ServiceEvent(new LFServiceError(EServiceErrorType.ERROR_TOKEN_INVALID, ((LFBaseResponse) response).getMessage()));
            }
        }
        return null;
    }

    private boolean isQuietService() {
        return null == serviceListener && (null == viewServiceListeners || viewServiceListeners.isEmpty());
    }

    private ServiceEvent generateMockServiceEvent() {

        LFServiceError serviceError = new LFServiceError();

        EServiceErrorType errorType = EServiceErrorType.ERROR_NONE;
        String errorMsg = LFApplication.getInstance().getString(R.string.service_error_common);

        if (LFServiceErrorMockModel.EMockServiceErrorType.MOCK_NO_NET == mockModel.getMockErrorType()) {
            errorType = EServiceErrorType.ERROR_NOT_CONNECTED;
            errorMsg = LFApplication.getInstance().getString(R.string.service_error_not_connected);
        } else if (LFServiceErrorMockModel.EMockServiceErrorType.MOCK_COMMON == mockModel.getMockErrorType()) {
            errorType = EServiceErrorType.ERROR_NONE;
            errorMsg = LFApplication.getInstance().getString(R.string.service_error_common);
        } else if (LFServiceErrorMockModel.EMockServiceErrorType.MOCK_TOKEN_INVALID == mockModel.getMockErrorType()) {
            errorType = EServiceErrorType.ERROR_TOKEN_INVALID;
            errorMsg = LFApplication.getInstance().getString(R.string.service_error_token_invalid);
        }
        serviceError.setErrorType(errorType);
        serviceError.setErrorMsg(errorMsg);

        ServiceEvent serviceEvent = new ServiceEvent(serviceError);

        return serviceEvent;
    }

    /**
     * cancel the request and
     * abandon the request callback when request.
     * should invoke this method when you do not care this request.
     */
    public void destroy() {
        isQuietService = true;
        if (!TextUtils.isEmpty(token)) {
            RequestManager.getInstance(LFApplication.getInstance()).getRequestQueue().cancelAll(token);
        }
    }
}
