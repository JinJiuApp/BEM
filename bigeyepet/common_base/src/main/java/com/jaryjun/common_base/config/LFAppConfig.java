package com.jaryjun.common_base.config;



import java.util.HashMap;

/**
 * Created by Ted on 2015/5/8.
 */
public class LFAppConfig {
    private static LFAppConfig instance = new LFAppConfig();
    public static String BuildTest = "TEST";
    public static String BuildDev = "DEV";
    public static String BuildBeta = "BETA";
    public static String BuildDemo = "DEMO";
    public static String BuildSim = "SIM";
    public static String BuildPro = "PRODUCTION";

    private static int versionCode = 1;
    private static String versionName = "1.0";
    private static String channelNo = "wukong";
    private static boolean isShowJson = true;

    /**
     * 五种编译环境，开发版本，测试版本(提供给测试)，Beta版本，演示版本，仿真版本,生产版本(上线)
     */
    public enum BuildEssential {
        DEV, TEST, BETA, DEMO, SIM, PRODUCTION
    }

    private static BuildEssential buildEssential = BuildEssential.DEV;

    public static LFAppConfig getInstance() {
        if (instance == null) {
            synchronized (LFAppConfig.class) {
                if (instance == null) {
                    instance = new LFAppConfig();
                }
            }
        }
        return instance;
    }


    public static boolean isProduction() {
        if (buildEssential.equals(BuildEssential.PRODUCTION)) {
            return true;
        }
        return false;
    }

    public static boolean isDev() {
        if (buildEssential.equals(BuildEssential.DEV)) {
            return true;
        }
        return false;
    }

    public static boolean isTest() {
        if (buildEssential.equals(BuildEssential.TEST)) {
            return true;
        }
        return false;
    }

    public static BuildEssential getBuildEssential() {
        return buildEssential;
    }

    public static void setBuildEssential(BuildEssential buildEssential) {
        LFAppConfig.buildEssential = buildEssential;
        setIsShowJson(!isProduction());
    }

    public static boolean isShowJson() {
        return isShowJson;
    }

    public static void setIsShowJson(boolean isShowJson) {
        LFAppConfig.isShowJson = isShowJson;
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static void setVersionCode(int versionCode) {
        LFAppConfig.versionCode = versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static void setVersionName(String versionName) {
        LFAppConfig.versionName = versionName;
    }

    public static String getChannelNo() {
        return channelNo;
    }

    public static void setChannelNo(String channelNo) {
        LFAppConfig.channelNo = channelNo;
    }

    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Accept", "application/json; charset=UTF-8");
        header.put("Accept-Encoding", "gzip, deflate");
        header.put("os", "android");
        header.put("version", LFAppConfig.getVersionName());
        header.put("channel", LFAppConfig.getChannelNo());
  /*      header.put("deviceId", LFUserGlobalCache.getIns().getDeviceID());
        header.put("muid", DeviceUtil.getMuId());
        header.put("guestId", String.valueOf(LFUserInfoOps.getGuestId()));
        header.put("User-ID", String.valueOf(LFUserInfoOps.getGuestId()));
        header.put("token", LFUserInfoOps.getLoginToken());
        header.put("userMobile", MD5Util.getMD5String(LFUserInfoOps.getPhoneNumber()));*/
        return header;
    }
}
