package com.jaryjun.common_base.config;

/**
 * Created by youj on 2015/4/14.
 * ServiceConfig
 */
public class ServiceConfig {
    /***
     * 普通用户模式的服务地址
     */
    private static String USER_BIZ_IP = "";

    private static Integer USER_BIZ_PORT;

    private static String USER_BIZ_URL_PRE = "";

    public static void initUserIp(String ip, Integer port) {
        USER_BIZ_IP = ip;
        USER_BIZ_PORT = port;
        USER_BIZ_URL_PRE = "http://" + USER_BIZ_IP + ":" + USER_BIZ_PORT + "/";
    }

    public static String getUserBizIp() {
        return USER_BIZ_IP;
    }

    public static String getUerBizUrl() {
        return USER_BIZ_URL_PRE;
    }

    /**
     * 新房业务模式下请求服务的URL前缀
     */
    private static String NH_BIZ_IP = "";

    private static Integer NH_BIZ_PORT;

    private static String NH_BIZ_URL_PRE = "";

    public static void initNewHouseIp(String ip, Integer port) {
        NH_BIZ_IP = ip;
        NH_BIZ_PORT = port;
        NH_BIZ_URL_PRE = "http://" + NH_BIZ_IP + ":" + NH_BIZ_PORT + "/";
    }

    public static String getNhBizIp() {
        return NH_BIZ_IP;
    }

    public static String getNewHouseBizUrl() {
        return NH_BIZ_URL_PRE;
    }

    /**
     * 房东业务模式下请求服务的URL前缀
     */
    private static String LANDLORD_BIZ_IP = "";

    private static Integer LANDLORD_BIZ_PORT;

    private static String LANDLORD_BIZ_URL_PRE = "";

    public static void initLandlordIp(String ip, Integer port) {
        LANDLORD_BIZ_IP = ip;
        LANDLORD_BIZ_PORT = port;
        LANDLORD_BIZ_URL_PRE = "http://" + LANDLORD_BIZ_IP + ":" + LANDLORD_BIZ_PORT + "/";
    }

    public static String getLandlordBizIp() {
        return LANDLORD_BIZ_IP;
    }

    public static Integer getLandlordBizPort() {
        return LANDLORD_BIZ_PORT;
    }

    public static String getLandlordUrl() {
        return LANDLORD_BIZ_URL_PRE;
    }

    /**
     * 金融业务模式下请求服务的URL前缀
     */
    private static String FINANCE_BIZ_IP = "";

    private static Integer FINANCE_BIZ_PORT;

    private static String FINANCE_BIZ_URL_PRE = "";

    public static void initFinanceIp(String ip, Integer port) {
        FINANCE_BIZ_IP = ip;
        FINANCE_BIZ_PORT = port;
        FINANCE_BIZ_URL_PRE = "http://" + FINANCE_BIZ_IP + ":" + FINANCE_BIZ_PORT + "/";
    }

    public static String getFinanceBizUrl() {
        return FINANCE_BIZ_URL_PRE;
    }

    /**
     * 租房业务模式下请求服务的URL前缀
     */
    private static String RENT_BIZ_IP = "";

    private static Integer RENT_BIZ_PORT;

    private static String RENT_BIZ_URL_PRE = "";

    public static void initRentIp(String ip, Integer port) {
        RENT_BIZ_IP = ip;
        RENT_BIZ_PORT = port;
        RENT_BIZ_URL_PRE = "http://" + RENT_BIZ_IP + ":" + RENT_BIZ_PORT + "/";
    }

    public static String getRentBizUrl() {
        return RENT_BIZ_URL_PRE;
    }

    /**
     * 用户其他业务模式下通用IP前缀
     */
    private static String USER_OTHER_IP = "";

    private static Integer USER_OTHER_PORT;

    private static String USER_OTHER_URL_PRE = "";

    public static void initUserOtherIp(String ip, Integer port) {
        USER_OTHER_IP = ip;
        USER_OTHER_PORT = port;
        USER_OTHER_URL_PRE = "http://" + USER_OTHER_IP + ":" + USER_OTHER_PORT + "/";
    }

    public static String getUserOtherUrl() {
        return USER_OTHER_URL_PRE;
    }
}
