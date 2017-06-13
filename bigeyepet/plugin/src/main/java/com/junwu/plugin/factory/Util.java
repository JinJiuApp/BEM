package com.junwu.plugin.factory;

import android.content.Context;

import com.jaryjun.common_base.utils.ToastUtil;


/**
 * Created by Glen on 2016/6/14.
 * To modify the relevant code, please contact the developer Glen
 */
public class Util {

    public static void unInstallPlugin(Context context) {
        if (context == null) return;
        ToastUtil.show(context, "app project has not add relevant plugin");
    }
}
