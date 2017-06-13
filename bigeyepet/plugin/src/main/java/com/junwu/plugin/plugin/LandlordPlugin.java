package com.junwu.plugin.plugin;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public interface LandlordPlugin extends BasePlugin {
    /**
     * MainActivity中房东端Fragment
     */
    Class<? extends Fragment> getLandLordMainFragment();

    /**
     * 打开房东端，我的房源列表
     */
    void startLandHouseListActivity(Context context);

    /**
     * 打开房东端房源详情页面
     */
    void startLandHouseDetail(Context context, int id);

    /**
    *打开房东首界面
    */
    void startLdMainActivity(Context context);
}
