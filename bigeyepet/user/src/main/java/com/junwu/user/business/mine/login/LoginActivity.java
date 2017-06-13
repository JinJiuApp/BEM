package com.junwu.user.business.mine.login;

import android.os.Bundle;
import android.view.Window;

import com.jaryjun.common_base.ops.LFFragmentOps;
import com.jaryjun.common_base.user.LFBaseActivity;


/**
 * Created by Qinbaoyuan on 2015/5/28.
 * Modify by wxy on 2015/11/13
 */
//Test Git By wxy
public class LoginActivity extends LFBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginFragment loginFragment = new LoginFragment();
        LFFragmentOps.initFragment(getSupportFragmentManager(), loginFragment, "LOGIN", Window.ID_ANDROID_CONTENT);
    }

}
