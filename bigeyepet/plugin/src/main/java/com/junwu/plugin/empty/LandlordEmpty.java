package com.junwu.plugin.empty;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.junwu.plugin.factory.Util;
import com.junwu.plugin.plugin.LandlordPlugin;


/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public class LandlordEmpty implements LandlordPlugin {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public Class<? extends Fragment> getLandLordMainFragment() {
        return EmptyFragment.class;
    }

    @Override
    public void startLandHouseListActivity(Context context) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void startLandHouseDetail(Context context, int id) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void startLdMainActivity(Context context) {
        Util.unInstallPlugin(this.context);
    }
}
