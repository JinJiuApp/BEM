package com.jaryjun.common_base.ops;

import android.content.Context;
import android.content.Intent;

import com.jaryjun.common_base.user.LFApplication;
import com.jaryjun.common_base.utils.HomeActionType;


/**
 * Created by Glen on 2015/11/12.
 */
public class LFSystemOps {

    /**
     * 处理单点登录
     */
    public static void handleTokenInvalid(String msg) {
        if (!LFUserInfoOps.isUserLogin()) return;

        LFUserInfoOps.clearUserInfo();
        try {
            Context context = LFApplication.getInstance().getApplicationContext();
            Intent intent = new Intent(context, Class.forName("com.wukong.user.home.LFUserHomeActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(HomeActionType.TOKEN_INVALID_MSG_KEY, msg);
            intent.putExtra(HomeActionType.ACTION_KEY, HomeActionType.ACTION_TO_RE_LOGIN);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
