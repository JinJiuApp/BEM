package com.jaryjun.common_base.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.utils.PreferenceUtil;


/**
 * Created by youj on 2015/5/6.
 * LFApplication
 */
public class LFApplication extends Application {

    private static LFApplication instance;

    private PreferenceUtil mPref;

    public static LFApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        mPref = new PreferenceUtil(getResources(), sharedPreferences);
        getPref().applyBoolean(R.string.is_app_active, false);
        getPref().applyBoolean(R.string.is_on_conversation, false);


    }

 /*   @Override
    protected void attachBaseContext(Context base) {//解决65536问题 http://blog.csdn.net/t12x3456/article/details/40837287
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    public PreferenceUtil getPref() {
        return mPref;
    }


}
