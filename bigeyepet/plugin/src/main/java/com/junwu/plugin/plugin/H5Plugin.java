package com.junwu.plugin.plugin;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public interface H5Plugin extends BasePlugin {

    /**
     * 跳到卖家服务
     */
    void jump2SellerService(Context context, String url);

    /**
     * 打开悟空精选和悟空小秘书
     */
    void startWuKongSelected(Context context, String url);

    /**
     * 打开common Activity
     */
    void openCommon(Context context, String title, String url);


    void openADBanner(Context context, String title, String url);
    /**
     * 获取user h5 fragment
     */
    Fragment getUserH5Fragment();

    /**
     * user h5页面返回
     */
    void execGoBackByUserH5Fragment(Fragment fragment);

    /**
     * user h5页面能否返回
     */
    boolean execIsCanBackByUserH5Fragment(Fragment fragment);
}
