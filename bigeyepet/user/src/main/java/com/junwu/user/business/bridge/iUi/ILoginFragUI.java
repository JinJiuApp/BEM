package com.junwu.user.business.bridge.iUi;

import com.junwu.common_net.base.IUi;
import com.junwu.user.business.servicemodel.response.LoginResponse;


/**
 * Created by Qinbaoyuan on 2015/5/5.
 * ILoginFragUI
 */
public interface ILoginFragUI extends IUi {
    String getInputMobileNumber();
    String getInputSecurityCode();
    void refreshTextView(int seconds);
    void loginSucceed(LoginResponse response);
    void loginFailed(String failMsg);
    void focusEditTextView(int type);
    void finishActivity();
    void updateUserInfo(String name, int sex);

}
