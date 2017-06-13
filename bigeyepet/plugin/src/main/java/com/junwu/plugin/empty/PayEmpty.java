package com.junwu.plugin.empty;

import android.content.Context;

import com.junwu.plugin.factory.Util;
import com.junwu.plugin.plugin.PayPlugin;


/**
 * Created by Glen on 2016/6/12.
 * To modify the relevant code, please contact the developer Glen
 */
public class PayEmpty implements PayPlugin {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String getWeiXinPayId() {
        Util.unInstallPlugin(this.context);
        return null;
    }

    /*@Override
    public void startPayActivity(Context context, SPayModel request, int requestCode) {
        Util.unInstallPlugin(this.context);
    }*/
}
