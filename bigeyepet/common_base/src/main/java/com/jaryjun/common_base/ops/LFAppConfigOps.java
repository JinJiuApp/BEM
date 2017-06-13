package com.jaryjun.common_base.ops;

import android.text.TextUtils;

import com.jaryjun.common_base.config.LFAppConfig;


/**
 * Created by Ted on 14-7-28.
 * LFAppConfigOps
 */
public class LFAppConfigOps {

    public static boolean isProduction() {
        return LFAppConfig.isProduction();
    }

    public static boolean isBeta() {
        return LFAppConfig.getBuildEssential().equals(LFAppConfig.BuildEssential.BETA);
    }

    public static boolean isDev() {
        return LFAppConfig.getBuildEssential().equals(LFAppConfig.BuildEssential.DEV);
    }

    public static boolean isTest() {
        return LFAppConfig.getBuildEssential().equals(LFAppConfig.BuildEssential.TEST);
    }

    public static boolean isDemo() {
        return LFAppConfig.getBuildEssential().equals(LFAppConfig.BuildEssential.DEMO);
    }

    public static boolean isSim() {
        return LFAppConfig.getBuildEssential().equals(LFAppConfig.BuildEssential.SIM);
    }

    /***
     * 是否是百度渠道包，因为百度渠道包要单独做统计
     *
     * @return boolean
     */
    public static boolean isBaiDuChannel() {
        return (!TextUtils.isEmpty(LFAppConfig.getChannelNo())) &&
                "PbaiduMobads".equals(LFAppConfig.getChannelNo());
    }

    /***
     * 获取当前app的版本号
     *
     * @return
     */
    public static int getAppVersionCode() {
        return LFAppConfig.getVersionCode();
    }

}
