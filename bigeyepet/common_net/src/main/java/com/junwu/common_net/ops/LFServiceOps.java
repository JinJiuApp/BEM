package com.junwu.common_net.ops;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.jaryjun.common_base.component.dialog.BaseDialogFragment;
import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.jaryjun.common_base.component.dialog.DialogType;
import com.jaryjun.common_base.config.ServiceConfig;
import com.jaryjun.common_base.constant.BaseConstants;
import com.junwu.common_net.base.IUi;
import com.junwu.common_net.base.LFBaseRequest;
import com.junwu.common_net.base.LFBaseServiceFragment;
import com.junwu.common_net.server.LFServiceReqModel;
import com.junwu.common_net.server.LFServiceTask;
import com.junwu.common_net.server.ViewServiceListener;
import com.junwu.common_net.utils.NetProgressDialog;
import com.junwu.pet_net.annotation.RequestAnnotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youj on 2015/4/21.
 */
public class LFServiceOps {

    /**
     * 静默请求，无需loadingLayout转圈和接收服务回调
     *
     * @param request
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T> LFServiceTask loadData(LFBaseRequest request, Class<T> responseClass) {
        LFServiceReqModel.Builder builder = new LFServiceReqModel.Builder();
        builder.setRequest(request).setResponseClass(responseClass);
        return loadData(builder.build(), null);
    }

    public static LFServiceTask loadData(LFServiceReqModel reqModel, final IUi iui) {
        String path = checkReqModel(reqModel);

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        String token = path + System.currentTimeMillis();
        String urlPre = ServiceConfig.getUerBizUrl();
        if (BaseConstants.APP_BIZ_NEW_HOUSE == reqModel.getBizName()) {
            urlPre = ServiceConfig.getNewHouseBizUrl();
        } else if (BaseConstants.APP_BIZ_LANDLORD == reqModel.getBizName()) {
            urlPre = ServiceConfig.getLandlordUrl();
        } else if (BaseConstants.APP_BIZ_FINANCE == reqModel.getBizName()) {
            urlPre = ServiceConfig.getFinanceBizUrl();
        } else if (BaseConstants.APP_BIZ_RENT_HOUSE == reqModel.getBizName()) {
            urlPre = ServiceConfig.getRentBizUrl();
        }
//        String proxy_url = "http://10.0.18.240/api/request?host=" + urlPre + "&path=" + path;
//        reqModel.getRequest().setUrl(LFAppConfigOps.isDev() ? proxy_url : (urlPre + path));
        reqModel.getRequest().setUrl(urlPre + path);
        reqModel.getRequest().setToken(token);

        if (reqModel.getCacheControl() != null) {
            String cacheKey = reqModel.getCacheControl().getCacheKey();
            reqModel.getCacheControl().setCacheKey(cacheKey == null ? (urlPre + path) : (urlPre + path + cacheKey));
        }

        BaseDialogFragment baseDialogFragment = null;
        //显示全局遮罩
        if (reqModel.isShowCoverProgress()) {
            if (iui instanceof LFBaseServiceFragment) {
                DialogExchangeModel.DialogExchangeModelBuilder builder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.PROGRESS, token);
                builder.setBackAble(false).setSpaceAble(false);
                DialogExchangeModel dialogExchangeModel = builder.create();
                if (dialogExchangeModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BaseDialogFragment.TAG, dialogExchangeModel.dialogExchangeModelBuilder);
                    baseDialogFragment = NetProgressDialog.getInstance(bundle);
                }
                if (baseDialogFragment != null) {
                    FragmentTransaction ft = ((LFBaseServiceFragment) iui).getFragmentManager().beginTransaction();
                    ft.add(baseDialogFragment, dialogExchangeModel.getTag());
                    ft.commitAllowingStateLoss();
                }
            }
        }

        List<ViewServiceListener> viewServiceListeners = new ArrayList<>();
        if (null != iui && null != iui.getViewServiceListeners()) {
            viewServiceListeners.addAll(iui.getViewServiceListeners());
        }

        if (null != baseDialogFragment && baseDialogFragment instanceof NetProgressDialog) {
            viewServiceListeners.add((NetProgressDialog) baseDialogFragment);
        }

        LFServiceTask task = new LFServiceTask(reqModel.getServiceListener(), viewServiceListeners, reqModel.getMockModel(), reqModel.isProcessServiceError());
        task.sendService(reqModel.getRequest(), reqModel.getResponseClass(), reqModel.getCacheControl(), iui);
        return task;
    }

    private static String checkReqModel(LFServiceReqModel reqModel) {
        if (null == reqModel || null == reqModel.getRequest() || null == reqModel.getResponseClass()) {
            return null;
        }

        //check path
        RequestAnnotation
                requestAnnotation = reqModel.getRequest().getClass().getAnnotation(RequestAnnotation.class);
        if (requestAnnotation == null || TextUtils.isEmpty(requestAnnotation.path())) {
            return null;
        }

        return requestAnnotation.path();
    }
}
