package com.junwu.plugin.empty;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.junwu.plugin.factory.Util;
import com.junwu.plugin.plugin.H5Plugin;


/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public class H5Empty implements H5Plugin {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void jump2SellerService(Context context, String url) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void startWuKongSelected(Context context, String url) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openCommon(Context context, String title, String url) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openADBanner(Context context, String title, String url) {

    }

    @Override
    public Fragment getUserH5Fragment() {
        return new EmptyFragment();
    }

    @Override
    public void execGoBackByUserH5Fragment(Fragment fragment) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public boolean execIsCanBackByUserH5Fragment(Fragment fragment) {
        Util.unInstallPlugin(this.context);
        return false;
    }
}
