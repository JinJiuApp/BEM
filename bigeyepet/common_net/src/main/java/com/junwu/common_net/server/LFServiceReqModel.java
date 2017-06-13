package com.junwu.common_net.server;


import com.jaryjun.common_base.constant.BaseConstants;
import com.junwu.common_net.base.LFBaseRequest;

/**
 * Created by Administrator on 2015/05/09.
 * 服务请求构建对象
 */
public class LFServiceReqModel {

    private Builder builder;

    public LFServiceReqModel(Builder builder) {
        this.builder = builder;
    }

    public LFBaseRequest getRequest() {
        return builder.request;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getResponseClass() {
        return builder.responseClass;
    }

    public boolean isShowCoverProgress() {
        return builder.isShowCoverProgress;
    }

    @SuppressWarnings("unchecked")
    public <T> OnServiceListener<T> getServiceListener() {
        return builder.serviceListener;
    }

    public ServiceCacheControl getCacheControl() {
        return builder.serviceCacheControl;
    }

    public boolean isProcessServiceError() {
        return builder.isProcessServiceError;
    }

    public int getBizName() {
        return builder.bizName;
    }

    public LFServiceErrorMockModel getMockModel() {
        return builder.mockModel;
    }

    public static class Builder<T> {

        public Builder() {

        }

        /**
         * 请求实体
         */
        private LFBaseRequest request;

        /**
         * 响应实体类型
         */
        private Class<T> responseClass;

        /**
         * 服务回调接口
         */
        private OnServiceListener<T> serviceListener;

        /**
         * 缓存控制
         */
        private ServiceCacheControl serviceCacheControl;

        /**
         * 是否显示全局遮罩, 默认false
         */
        private boolean isShowCoverProgress;

        /**
         * 是否要进行统一的服务错误处理, 默认为true
         */
        private boolean isProcessServiceError = false;

        /**
         * 当前业务模式名称，默认为二手房业务
         */
        private int bizName = BaseConstants.APP_BIZ_OWN_HOUSE;

        private LFServiceErrorMockModel mockModel;

        public Builder setRequest(LFBaseRequest request) {
            this.request = request;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder setResponseClass(Class<T> responseClass) {
            this.responseClass = responseClass;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder setServiceListener(OnServiceListener<T> serviceListener) {
            this.serviceListener = serviceListener;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setCacheControl(ServiceCacheControl serviceCacheControl) {
            this.serviceCacheControl = serviceCacheControl;
            return this;
        }

        public Builder setShowCoverProgress(boolean isShowCoverProgress) {
            this.isShowCoverProgress = isShowCoverProgress;
            return this;
        }

        public Builder setProcessServiceError(boolean isProcessServiceError) {
            this.isProcessServiceError = isProcessServiceError;
            return this;
        }

        public Builder setBizName(int bizName) {
            this.bizName = bizName;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setMockModel(LFServiceErrorMockModel mockModel) {
            this.mockModel = mockModel;
            return this;
        }

        public LFServiceReqModel build() {
            return new LFServiceReqModel(this);
        }
    }
}
