package com.junwu.user.presenter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaryjun.common_base.component.db.LFDBManager;
import com.jaryjun.common_base.component.dialog.CustomerFragmentCallBack;
import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.jaryjun.common_base.component.dialog.DialogType;
import com.jaryjun.common_base.ops.LFImageLoaderOps;
import com.jaryjun.common_base.ops.LFUiOps;
import com.jaryjun.common_base.user.LFApplication;
import com.junwu.common_net.base.Presenter;
import com.junwu.common_net.ops.LFServiceOps;
import com.junwu.common_net.server.LFServiceError;
import com.junwu.common_net.server.LFServiceReqModel;
import com.junwu.common_net.server.OnServiceListener;
import com.junwu.user.R;
import com.junwu.user.business.httpmodel.request.GetVersionInfoRequest;
import com.junwu.user.business.httpmodel.response.GetVersionInfoResponse;
import com.junwu.user.ui.impui.IHomeActivityUI;

import java.util.ArrayList;
import java.util.List;

import dao.BottomBarItem;

/**
 * Created by youj on 2015/7/2.
 * HomeActivityPresenter
 */
public class HomeActivityPresenter extends Presenter implements View.OnClickListener {

    private IHomeActivityUI ui;

    private String mNewVersionUrl;
    private String mNewVersion;
    private boolean bIsForceUpdate;
    private String mNewVersionMsg;

    public HomeActivityPresenter(IHomeActivityUI ui) {
        this.ui = ui;
    }

    @Override
    public void onClick(View v) {
        /*if (Avoid2Click.isFastDoubleClick()) return;
        if (v.getId() == R.id.app_update_negative_btn) {
            ui.exitUpdateDialog(bIsForceUpdate);
        } else if (v.getId() == R.id.app_update_positive_btn) {
            ui.processVersionUpdate(mNewVersion, mNewVersionUrl, bIsForceUpdate);
        }*/
    }

    /**
     * 版本更新信息回调接口
     */
    private OnServiceListener<GetVersionInfoResponse> mVersionInfoOnServiceListener =
            new OnServiceListener<GetVersionInfoResponse>() {
                @Override
                public void onServiceSuccess(GetVersionInfoResponse response, String token) {
                    /*if (null != response && response.succeeded() && null != response.getVersionInfo()) {
                        VersionInfoModel versionInfo = response.getVersionInfo();
                        bIsForceUpdate = versionInfo.getIsForce();
                        mNewVersionMsg = versionInfo.getUpdateMessage();
                        mNewVersion = versionInfo.getVersionName();
                        mNewVersionUrl = versionInfo.getUrl();
                        String nowVersion = LFAppConfig.getVersionName();
                        if (LFDeviceOps.isHaveNewVersion(mNewVersion, nowVersion, mNewVersionUrl)) {
                            if (bIsForceUpdate) {
                                showNewVersionDialog();
                            } else {
                                //对比上次提醒更新的版本，防止频繁提醒
                                PreferenceUtil pre = ((LFBaseActivity) ui).getPref();
                                String lastRemindVersion = pre.getString(R.string.last_remind_update_version, "");
                                if (!mNewVersion.equals(lastRemindVersion)) {
                                    showNewVersionDialog();
                                    pre.commitString(R.string.last_remind_update_version, mNewVersion);
                                }
                            }
                        }
                    }*/
                }

                @Override
                public void onServiceFail(LFServiceError error, String token) {
                    super.onServiceFail(error, token);
                }
            };

    /***
     * 获取最新版本信息
     */
    @SuppressWarnings("unchecked")
    public void getVersionInfo() {
        GetVersionInfoRequest request = new GetVersionInfoRequest();
        request.setOs("android");
        LFServiceReqModel.Builder builder = new LFServiceReqModel.Builder();
        builder.setRequest(request)
                .setResponseClass(GetVersionInfoResponse.class)
                .setServiceListener(mVersionInfoOnServiceListener);
        LFServiceOps.loadData(builder.build(), null);
    }

    /**
     * 改变bar icon和title
     */
    public void processChangeTabBarIcon(View... views) {
        List<Integer> index = new ArrayList<>();
        List<StateListDrawable> drawables = new ArrayList<>();
        List<ColorStateList> colors = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for (BottomBarItem model : LFDBManager.getIns().getBottomBarDao().queryBuilder().build().list()) {
            //校验索引
            if (model.getId() > views.length || model.getId() < 1) {
                //id值1-5，代表底部bar的从左到右的顺序
                return;
            }

            //校验name
            if (model.getName() != null && model.getName().trim().length() > 4) {
                //底部bar单个文字限制4个字符及以下
                return;
            }

            //校验icon
            Drawable selected = LFImageLoaderOps.getCacheImage(model.getMouseOnIcon(), -1, -1);
            Drawable unselected = LFImageLoaderOps.getCacheImage(model.getMouseOutIcon(), -1, -1);
            if (selected == null || unselected == null) {
                //后台Icon下载完成
                return;
            }

            //校验color
            try {
                int color = new java.math.BigInteger(model.getMouseOnColor().substring(2, model.getMouseOnColor().length()), 16).intValue();
                int unColor = new java.math.BigInteger(model.getMouseOutColor().substring(2, model.getMouseOutColor().length()), 16).intValue();

                index.add(model.getId().intValue() - 1);
                names.add(model.getName().trim());
                colors.add(getColorStates(color, unColor));
                drawables.add(getStateListDrawable(selected, unselected));
            } catch (NumberFormatException e) {
                return;
            }
        }

        //数据校验通过，进行UI设置
        for (int id : index) {
            if (views[id] instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) views[id]).getChildCount(); i++) {
                    View child = ((ViewGroup) views[id]).getChildAt(i);
                    if (child instanceof ViewGroup) {
                        for (int j = 0; j < ((ViewGroup) child).getChildCount(); j++) {
                            View nChild = ((ViewGroup) child).getChildAt(j);
                            if (nChild instanceof ImageView) {
                                if (names.get(id) == null || names.get(id).trim().length() == 0) {
                                    nChild.setLayoutParams(new RelativeLayout.LayoutParams(LFUiOps.dip2px(46), LFUiOps.dip2px(46)));
                                }
                                nChild.setBackgroundDrawable(drawables.get(id));
                                break;
                            }
                        }
                    }
                    if (child instanceof TextView) {
                        if (names.get(id) == null || names.get(id).trim().length() == 0) {
                            child.setVisibility(View.GONE);
                        } else {
                            ((TextView) child).setTextColor(colors.get(id));
                            ((TextView) child).setText(names.get(id));
                        }
                    }
                }
            }
        }
    }

    private StateListDrawable getStateListDrawable(Drawable selected, Drawable unselected) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, selected);
        drawable.addState(new int[]{android.R.attr.state_pressed}, selected);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, unselected);
        return drawable;
    }

    private ColorStateList getColorStates(int selected, int unselected) {
        ColorStateList colors = new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_selected}, new int[]{android.R.attr.state_pressed},
                new int[]{-android.R.attr.state_pressed}
        }, new int[]{selected, selected, unselected});
        return colors;
    }

    /**
     * 处理启动进入的业务选择逻辑
     * 定位成功  默认进入定位城市有数据的地图页，如果既有二手房又有新房，默认在二手房地图
     * 定位失败  默认进入上海二手房地图页（首次）    上次离开的位置（非首次进入）
     */
    public void handleCitySelected() {
        /*SMapLocationModel location = Plugs.getInstance().createMapPlug().getCacheLocation();
        if (location != null) {
            CityModel cityModel = LFCityOps.getCityModelByLocation(location.getProvince(), location.getCity());
            if (cityModel != null && cityModel.getSupportBizList() != null) {
                LFCityOps.setCurrCity(cityModel);//设置当前城市，并下载该城市广告图片信息
            }
        }*/
    }

    /**
     * 显示新版本更新的提示框
     */
    private void showNewVersionDialog() {
        if (TextUtils.isEmpty(mNewVersionMsg)) {
            mNewVersionMsg = LFApplication.getInstance()
                    .getString(
                            bIsForceUpdate ? R.string.version_update_tips_force : R.string.version_update_tips);
        }
        DialogExchangeModel.DialogExchangeModelBuilder builder;
        builder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.CUSTOMER, "UPDATE");
        builder.setBackAble(false)
                .setSpaceAble(false)
                .setCustomerFragmentCallBack(mUpdateDialogCallBack);
        ui.showNewVersionDialog(builder.create());
    }

    private CustomerFragmentCallBack mUpdateDialogCallBack = new CustomerFragmentCallBack() {
        @Override
        public View getCustomerView(String mTag) {
            View view = View.inflate((Context) ui, R.layout.update_app_dialog_layout, null);
            setUpdateDialog(view);
            return view;
        }
    };

    private void setUpdateDialog(View dialogView) {
       /* String title = "V " + mNewVersion + " 悟空升级";
        ((TextView) dialogView.findViewById(R.id.app_update_dialog_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.app_update_dialog_content)).setText(mNewVersionMsg);
        ((TextView) dialogView.findViewById(R.id.app_update_negative_txt)).setText(
                bIsForceUpdate ? "退出应用" : "暂不升级");
        dialogView.findViewById(R.id.app_update_negative_btn).setOnClickListener(this);
        dialogView.findViewById(R.id.app_update_positive_btn).setOnClickListener(this);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = LFUiOps.dip2px((Context) ui, 200f);
        layoutParams.width = LFUiOps.getScreenWidth((Context) ui) - LFUiOps.dip2px((Context) ui, 72f);
        dialogView.setLayoutParams(layoutParams);*/
    }
}
