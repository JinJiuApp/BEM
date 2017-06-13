package com.jaryjun.common_base.ops;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.model.UserInfoModel;
import com.jaryjun.common_base.user.LFApplication;
import com.jaryjun.common_base.user.LFGlobalCache;
import com.jaryjun.common_base.utils.PreferenceUtil;

import java.lang.reflect.Method;

/**
 * Created by Ted on 2015/4/23.
 */
public class LFUserInfoOps {

    /**
     * 初始化用户信息
     */
    public static void initUserInfo() {
        UserInfoModel model = new UserInfoModel();

        PreferenceUtil pref = LFApplication.getInstance().getPref();
        model.setUserId(pref.getLong(R.string.user_id, -1l));
        model.setUserPhoneNum(pref.getString(R.string.user_phone, ""));
        model.setUserIntegral(pref.getInt(R.string.user_integral, -1));
        model.setUserName(pref.getString(R.string.user_name, ""));
        model.setUserSex(pref.getInt(R.string.user_sex, 0));
        model.setPhotoUrl(pref.getString(R.string.user_photo_url, ""));
        model.setLoginToken(pref.getString(R.string.login_key, ""));
        model.setPushName(pref.getString(R.string.push_name, ""));

        LFGlobalCache.getIns().setUserInfo(model);
    }

    /**
     * 登陆之后，返回了部分用户信息，用来更新用户信息
     *
     * @param model
     */
    public static void updateUserInfo(UserInfoModel model) {
        if (null != model) {
            LFGlobalCache.getIns().setUserInfo(model);
            PreferenceUtil pref = LFApplication.getInstance().getPref();
            pref.commitLong(R.string.user_id, model.getUserId());
            pref.commitString(R.string.user_phone, model.getUserPhoneNum());
            pref.commitInt(R.string.user_integral, model.getUserIntegral());
            pref.commitString(R.string.user_name, model.getUserName());
            pref.commitInt(R.string.user_sex, model.getUserSex());
            pref.commitString(R.string.user_photo_url, model.getPhotoUrl());
            pref.commitString(R.string.login_key, model.getLoginToken());
            pref.commitString(R.string.push_name, model.getPushName());
        }
    }

    /**
     * 把当前的userInfo，更新到 本地
     */
    public static void updateCurUserInfoToPref() {
        updateUserInfo(getUserInfo());
    }

    /**
     * 清空本地用户数据，退出时调用
     */
    public static void clearUserInfo() {
        LFGlobalCache.getIns().setUserInfo(new UserInfoModel());

        PreferenceUtil util = LFApplication.getInstance().getPref();
        util.commitLong(R.string.user_id, -1l);
        util.commitString(R.string.user_phone, "");
        util.commitInt(R.string.user_integral, 0);
        util.commitString(R.string.user_name, "");
        util.commitInt(R.string.user_sex, 0);
        util.commitString(R.string.user_photo_url, "");
        util.commitString(R.string.login_key, "");
        util.commitString(R.string.push_name, "");

        try {
            //ImUtils.logout();
            Method method = Class.forName("com.wukong.im.business.base.util.ImUtils").getMethod("logout");
            method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //LFRefreshManager.getIns().addRefreshType(LFRefreshManager.REFRESH_ALL_RECORD);
    }

    /**
     * 获取已登录的用户信息
     *
     * @return
     */
    public static UserInfoModel getUserInfo() {
        return LFGlobalCache.getIns().getUserInfo();
    }

    /**
     * 获取推送用户名
     *
     * @return
     */
    public static String getUserPushName() {
        UserInfoModel model = getUserInfo();
        return model == null ? null : model.getPushName();
    }

    /**
     * 获取用户登录token
     *
     * @return
     */
    public static String getLoginToken() {
        if (null != getUserInfo()) {
            return getUserInfo().getLoginToken();
        }
        return "";
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static long getGuestId() {
        if (null != getUserInfo()) {
            return getUserInfo().getUserId();
        }
        return -1;
    }

    /**
     * 获取用户手机号
     *
     * @return
     */
    public static String getPhoneNumber() {
        return isUserLogin() ? getUserInfo().getUserPhoneNum() : null;
    }

    /**
     * 用户是否登陆
     *
     * @return
     */
    public static boolean isUserLogin() {
        return (null != getUserInfo() && !TextUtils.isEmpty(getUserInfo().getUserPhoneNum()) && getUserInfo().getUserId() > 0);
    }

    /***
     * 验证指定的用户电话号码是否是当前登录的用户号码
     *
     * @param userPhone 用户号码
     * @return 是否是
     */
    public static boolean verifyUserPhone(@NonNull String userPhone) {
        if (TextUtils.isEmpty(userPhone)) return false;
        if (!TextUtils.isEmpty(getPhoneNumber())) {
            return getPhoneNumber().equals(userPhone);
        }
        return false;
    }
}
