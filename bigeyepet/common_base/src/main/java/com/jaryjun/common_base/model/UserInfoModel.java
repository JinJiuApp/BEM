package com.jaryjun.common_base.model;

import java.io.Serializable;

/**
 * Created by Glen on 2015/11/23.
 * 用户信息Model
 */
public class UserInfoModel implements Serializable {

    /**
     * 用户ID
     */
    private long userId = 0;//1

    /**
     * 用户手机号
     */
    private String userPhoneNum = "";//2

    /**
     * 用户积分
     */
    private int userIntegral;//3

    /**
     * 用户名
     */
    private String userName;//4

    /**
     * 用户性别
     */
    private int userSex;

    /***
     * 登录token
     */
    private String loginToken;

    /**
     * 头像
     */
    private String photoUrl;

    /**
     * 推送别名
     */
    private String pushName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public int getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(int userIntegral) {
        this.userIntegral = userIntegral;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
    }
}
