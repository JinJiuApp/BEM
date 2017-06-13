package com.jaryjun.bigeyepet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaryjun.bigeyepet.presenter.ISplashUi;
import com.jaryjun.common_base.user.LFBaseActivity;


/**
 * Created by youj on 2015/4/16.
 * 启动页面，程序入口
 */
public class LFUserSplashActivity extends LFBaseActivity implements ISplashUi {

    private ImageView mBootLoadImg;
    private TextView mTxtSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPref().applyBoolean(R.string.is_app_active, true);
        setContentView(R.layout.app_activity_splash_layout);



        startMainApp();
        //启动定位
       // Plugs.getInstance().createMapPlug().startLocation(null);



    }


    /**
     * 启动主页面
     */
    private void startMainApp() {
       // boolean isShowGuide = !getPref().getBoolean(getString(R.string.user_guide_page_has_show) + LFAppConfig.getVersionName(), false);
        Bundle bundle = null;
        if (null != getIntent())
            bundle = getIntent().getExtras();
     /*   if (isShowGuide) {
            Intent intent = new Intent(LFUserSplashActivity.this, LFUserGuideActivity.class);
            if (null != bundle) intent.putExtras(bundle);
            startActivity(intent);
        } else {*/
            Intent intent = new Intent(LFUserSplashActivity.this, MainApplication.getMainActivityClass());
            if (null != bundle) intent.putExtras(bundle);
            startActivity(intent);
        //}
        finish();
    }






}
