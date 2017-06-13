package com.junwu.user.business.model;

/**
 * Created by Ted on 2015/7/24.
 * 登录下发用户信息对象
 */
public class LoginResponseModel {
    private GuestInfo guestInfo;

    public GuestInfo getGuestInfo() {
        return guestInfo;
    }

    public void setGuestInfo(GuestInfo guestInfo) {
        this.guestInfo = guestInfo;
    }
}
