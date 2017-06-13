package com.junwu.user.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Glen on 2015/11/23.
 * 登录下发用户信息对象
 */
public class GuestInfo {
    /**
     * guestId : 734
     * phoneNum : 18053513757
     * name : 王卫国
     * sex : 1
     * guestPhotoUrl : null
     * loginKey : 49d3802d-ecbc-47e8-83f3-9aa0b2b510c9
     * imAccount : {"imAccount":"wkzf734","imPassword":"123456"}
     */

    private int guestId;
    private String phoneNum;
    private String name;
    private int sex;
    private String guestPhotoUrl;
    private String loginKey;
    private ImAccountEntity imAccount;
    /**
     *  推送识别信息
     */
    @JsonProperty("pushRecognition")
    private String pushAlias = "developer";

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setGuestPhotoUrl(String guestPhotoUrl) {
        this.guestPhotoUrl = guestPhotoUrl;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public void setImAccount(ImAccountEntity imAccount) {
        this.imAccount = imAccount;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public String getGuestPhotoUrl() {
        return guestPhotoUrl;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public ImAccountEntity getImAccount() {
        return imAccount;
    }

    public String getPushAlias() {
        return pushAlias;
    }

    public void setPushAlias(String pushAlias) {
        this.pushAlias = pushAlias;
    }

    /**
     * imAccount : wkzf734
     * imPassword : 123456
     */
    public static class ImAccountEntity {
        private String imAccount;
        private String imPassword;

        public void setImAccount(String imAccount) {
            this.imAccount = imAccount;
        }

        public void setImPassword(String imPassword) {
            this.imPassword = imPassword;
        }

        public String getImAccount() {
            return imAccount;
        }

        public String getImPassword() {
            return imPassword;
        }
    }
}
