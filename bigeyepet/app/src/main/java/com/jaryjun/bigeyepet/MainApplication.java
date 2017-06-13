package com.jaryjun.bigeyepet;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaryjun.common_base.component.exception.WKUncaughtExceptionHandler;
import com.jaryjun.common_base.config.LFAppConfig;
import com.jaryjun.common_base.ops.LFIoOps;
import com.jaryjun.common_base.ops.LFUserInfoOps;
import com.jaryjun.common_base.user.LFBaseActivity;
import com.junwu.plugin.PluginApplication;
import com.junwu.user.business.mine.login.LoginActivity;

import java.util.List;

/**
 * Created by youj on 2015/7/6. Test Git
 */
public class MainApplication extends PluginApplication {
    private static Class<? extends LFBaseActivity> mMainClass = LoginActivity.class;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppEnv();
        WKUncaughtExceptionHandler.getInstance().init(getApplicationContext());
    }

    private void initAppEnv() {
       // Logger.init("LF_LOG").setLogValve(LFAppConfig.isShowJson()).methodCount(1);

        //在手机外部内存中创建app文件夹
        LFIoOps.initBaseAppFolder();

        initAppStatus();

        initServerIp();
         //初始化图片加载器Universal-Image-Loader

         //初始化友盟统计
        //initStatisticsConfig();
       //初始化Mi推送


        //init userInfo
        LFUserInfoOps.initUserInfo();

        //初始化服务器时间差
      //  ServerTimeSync.syncServerDiffTime(getPref().getLong(R.string.server_diff_time, 0));
    }


    /**
     * 初始化服务端IP地址
     */
    private void initServerIp() {
       /* ServiceConfig.initUserIp(BuildConfig.USER_IP, BuildConfig.USER_PORT);
        ServiceConfig.initRentIp(BuildConfig.RENT_IP, BuildConfig.RENT_PORT);
        ServiceConfig.initNewHouseIp(BuildConfig.NEW_HOUSE_IP, BuildConfig.NEW_HOUSE_PORT);
        ServiceConfig.initLandlordIp(BuildConfig.LANDLORD_IP, BuildConfig.LANDLORD_PORT);
        ServiceConfig.initFinanceIp(BuildConfig.FINANCE_IP, BuildConfig.FINANCE_PORT);
        ServiceConfig.initUserOtherIp(BuildConfig.OTHER_IP, BuildConfig.OTHER_PORT);*/
    }

    /**
     * 初始化AppConfig参数
     */
    private void initAppStatus() {
        /**检查配置信息，确认编译环境*/
        LFAppConfig.BuildEssential buildEssential = LFAppConfig.BuildEssential.DEV;
        /*String buildConfig = BuildConfig.SERVER_ENV;
        if (TextUtils.isEmpty(buildConfig) || buildConfig.equalsIgnoreCase(LFAppConfig.BuildPro)) {
            buildEssential = LFAppConfig.BuildEssential.PRODUCTION;
        } else if (buildConfig.equalsIgnoreCase(LFAppConfig.BuildBeta)) {
            buildEssential = LFAppConfig.BuildEssential.BETA;
        } else if (buildConfig.equalsIgnoreCase(LFAppConfig.BuildDev)) {
            buildEssential = LFAppConfig.BuildEssential.DEV;
        } else if (buildConfig.equalsIgnoreCase(LFAppConfig.BuildTest)) {
            buildEssential = LFAppConfig.BuildEssential.TEST;
        } else if (buildConfig.equalsIgnoreCase(LFAppConfig.BuildDemo)) {
            buildEssential = LFAppConfig.BuildEssential.DEMO;
        } else if (buildConfig.equalsIgnoreCase(LFAppConfig.BuildSim)) {
            buildEssential = LFAppConfig.BuildEssential.SIM;
        } else {
            ToastUtil.show(this, "程序初始化服务器地址时失败，请重启应用！");
        }*/
        LFAppConfig.setBuildEssential(buildEssential);
        /** 初始化版本信息*/
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            LFAppConfig.setVersionCode(pi.versionCode);
            LFAppConfig.setVersionName(pi.versionName);
            //LFAppConfig.setChannelNo(ChannelUtil.getChannel(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



  /*  private void initStatisticsConfig() {
        UMengStatManager.getIns().init(this);
        UMengStatManager.getIns().setChannel(LFAppConfig.getChannelNo());
    }*/

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //Logger.d("收到回调--------------"+level);
        //if(level == TRIM_MEMORY_COMPLETE){
        //    Logger.d("触发了低内存回调");
        //    AppGCManager.setGCStatus(true);
        //}
    }

    public static Class<? extends LFBaseActivity> getMainActivityClass() {
        return mMainClass;
    }
}
