package com.junwu.plugin.plugin;

/**
 * Created by Glen on 2016/6/12.
 * To modify the relevant code, please contact the developer Glen
 */
public interface PayPlugin extends BasePlugin {
    /**
     * 获取微信支付Id
     */
    String getWeiXinPayId();

    /**
     * 打开支付页面
     */
    //void startPayActivity(Context context, SPayModel request, int requestCode);
}
