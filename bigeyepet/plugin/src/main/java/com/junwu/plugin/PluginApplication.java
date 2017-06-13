package com.junwu.plugin;


import com.jaryjun.common_base.user.LFApplication;

/**
 * Created by Glen on 2016/6/12.
 */
public class PluginApplication extends LFApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Plugs.getInstance().createUserPlug().init(getApplicationContext());
        Plugs.getInstance().createLandLordPlug().init(getApplicationContext());
        Plugs.getInstance().createRentPlug().init(getApplicationContext());
        Plugs.getInstance().createPayPlug().init(getApplicationContext());
        Plugs.getInstance().createH5Plug().init(getApplicationContext());
        Plugs.getInstance().createMapPlug().init(getApplicationContext());
    }
}