package com.junwu.user.business.servicemodel.response;


import com.junwu.common_net.base.LFBaseResponse;
import com.junwu.user.business.model.LoginResponseModel;

/**
 * Created by Qinbaoyuan on 2015/5/11.
 */
public class LoginResponse extends LFBaseResponse {

    private LoginResponseModel data;

    public LoginResponseModel getData() {
        return data;
    }

    public void setData(LoginResponseModel data) {
        this.data = data;
    }

}
