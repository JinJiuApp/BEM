package com.jaryjun.common_base.user;


import com.jaryjun.common_base.model.UserInfoModel;

/**
 * Created by youj on 2015/7/14.
 * <p/>
 * 全局缓存
 */
public class LFGlobalCache {

    private static LFGlobalCache instance;

    /**
     * 用户信息(空对象，避免外部链式调用时可能出现的NullPointerException)
     */
    private UserInfoModel userInfo = new UserInfoModel();

    /**
     * 是否显示红点("我的钱包")
     */
    private boolean isShowRedDot;

    public static LFGlobalCache getIns() {
        if (null == instance) {
            synchronized (LFGlobalCache.class) {
                if (null == instance) {
                    instance = new LFGlobalCache();
                }
            }
        }
        return instance;
    }

    public void setShowRedDot(boolean isShowRedDot) {
        this.isShowRedDot = isShowRedDot;
    }

    public boolean isShowRedDot() {
        return isShowRedDot;
    }

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
