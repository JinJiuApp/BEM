package com.jaryjun.common_base.utils;

/**
 * Created by glen on 2015/11/9.
 * 定义二级页面跳转HomeActivity的行为
 */
public class HomeActionType {
    /**
     * key
     */
    public static final String ACTION_KEY = "ACTION_KEY";
    public static final String TOKEN_INVALID_MSG_KEY = "MSG_KEY";

    /**
     * value -- 跳转主页'首页'TAB
     */
    public static final int ACTION_TO_USER = 0x01;
    public static final int ACTION_TO_USER_NEW_HOUSE = 0x011;
    public static final int ACTION_TO_USER_OWNED_HOUSE = 0x012;
    public static final int ACTION_TO_USER_RENT_HOUSE = 0x013;

    /**
     * value -- 跳转主页'记录'TAB
     */
    public static final int ACTION_TO_RECORD = 0x02;
    public static final int ACTION_TO_RECORD_OWNED = 0x021;
    public static final int ACTION_TO_RECORD_NEW = 0x022;
    public static final int ACTION_TO_RECORD_RENT = 0x023;
    /**
     * value -- 跳转主页'发现'TAB
     */
    public static final int ACTION_TO_DISCOVERY = 0x03;

    /**
     * value -- 跳转主页'我的'TAB
     */
    public static final int ACTION_TO_MINE = 0x04;

    /**
     * value -- 提示用户重新登录
     */
    public static final int ACTION_TO_RE_LOGIN = 0x05;
}
