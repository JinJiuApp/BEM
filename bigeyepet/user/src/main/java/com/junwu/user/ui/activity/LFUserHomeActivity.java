package com.junwu.user.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaryjun.common_base.component.dialog.BaseDialogFragment;
import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.jaryjun.common_base.component.event.MyEvent;
import com.jaryjun.common_base.component.widget.LFFragmentTabHost;
import com.jaryjun.common_base.ops.LFDialogOps;
import com.jaryjun.common_base.system.AppGCManager;
import com.jaryjun.common_base.user.Avoid2Click;
import com.jaryjun.common_base.user.LFBaseActivity;
import com.jaryjun.common_base.utils.ToastUtil;
import com.junwu.plugin.Plugs;
import com.junwu.user.R;
import com.junwu.user.business.home.LFUserMainFragment;
import com.junwu.user.business.mine.MineFragment;
import com.junwu.user.business.record.LFRecordFragment;
import com.junwu.user.presenter.HomeActivityPresenter;
import com.junwu.user.ui.MainTab;
import com.junwu.user.ui.impui.IHomeActivityUI;
import com.junwu.user.util.DownloaderUtil;

/**
 * Created by Glen on 2015/11/5.
 * LFUserHomeActivity
 */
public class LFUserHomeActivity extends LFBaseActivity
        implements IHomeActivityUI {
    //上一次点击返回的时间0
    private long LastBackTime = 0;
    private HomeActivityPresenter presenter;

    private View myDotView;//我的"右上角的红点
    private View findDotView;//发现"右上角的红点
    private LFFragmentTabHost mTabHost;

    //APP更新相关
    public ProgressBar mProgressBar;
    public TextView progressbar_download;

    Toast mToast;

    /***********************************************************************事件监听*************************************************************/
    /**
     * TAB 改变
     */
    private View.OnClickListener mOnTabClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (Avoid2Click.isFastDoubleClick()) return;
            int id = v.getId();
            if (id == R.id.tab_user) {
               // UMengStatManager.getIns().onActionEvent(LFUserHomeActivity.this, "2_0_zf");
                mTabHost.setCurrentTabByTag(MainTab.TAB_USER_TAG);
            } else if (id == R.id.tab_record) {
               // UMengStatManager.getIns().onActionEvent(LFUserHomeActivity.this, "2_0_jl");
                mTabHost.setCurrentTabByTag(MainTab.TAB_RECORD_TAG);
            } else if (id == R.id.tab_discovery) {
               // UMengStatManager.getIns().onActionEvent(LFUserHomeActivity.this, "2_0_fx");
                mTabHost.setCurrentTabByTag(MainTab.TAB_DISCOVERY_TAG);
            } else if (id == R.id.tab_mine) {
                mTabHost.setCurrentTabByTag(MainTab.TAB_MINE_TAG);
            }
        }
    };

    /***********************************************************************IHomeActivityUI*************************************************************/
    /**
     * 显示版本更新对话框
     */
    @Override
    public void showNewVersionDialog(DialogExchangeModel dialogExchangeModel) {
        if (null != getSupportFragmentManager().findFragmentByTag("UPDATE")) {
            ((BaseDialogFragment) getSupportFragmentManager().findFragmentByTag("UPDATE")).dismissSelf();
        }
        LFDialogOps.showDialogFragment(getSupportFragmentManager(), dialogExchangeModel);
    }

    /**
     * 处理版本更新
     */
    @Override
    public void processVersionUpdate(String newVersion, String newVersionUrl,
                                     boolean isForce) {
        if (null != getSupportFragmentManager().findFragmentByTag("UPDATE")) {
            ((BaseDialogFragment) getSupportFragmentManager().findFragmentByTag("UPDATE")).dismissSelf();
        }
        DownloaderUtil.getInstance().startDownloadApk(this, newVersionUrl, newVersion);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"),
                true, DownloaderUtil.getInstance()
                        .getDownloadObserver(null, DownloaderUtil.getInstance().getDownloadReference(), this));
        if (isForce) {
            showForceUpdateDialog();
        } else {
            Toast.makeText(LFUserHomeActivity.this, "悟空找房正在下载中...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void exitUpdateDialog(boolean isForceUpdate) {
        if (isForceUpdate) {
            quitApp();
        } else {
            if (null != getSupportFragmentManager().findFragmentByTag("UPDATE")) {
                ((BaseDialogFragment) getSupportFragmentManager().findFragmentByTag(
                        "UPDATE")).dismissSelf();
            }
        }
    }

    /************************************************************************ 公开方法************************************************************/

    /**
     * 跳转到指定Tab页面
     */
    public void jumpToTabPage(String tag) {
        if (MainTab.TAB_USER_TAG.equals(tag)
                || MainTab.TAB_RECORD_TAG.equals(tag)
                || MainTab.TAB_DISCOVERY_TAG.equals(tag)
                || MainTab.TAB_MINE_TAG.equals(tag)) {
            mTabHost.setCurrentTabByTag(tag);
        }
    }

    /**
     * 设置发现小红点是否显示
     *
     * @param flag
     */
    public void setFindDotIsShow(boolean flag) {
        if (findDotView != null)
            findDotView.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    /***
     * 检查新版本
     *
     * @param isForce 是否强制检查 （因为如果是非强制更新，用户点击了暂不更新，在本次app生命周期内，是不再提醒的，如果设置了true，就继续提醒）
     */
    public void checkNewVersion(boolean isForce) {
        if (isForce) {
            //将上一次提醒更新的版本置空，每次重新开始app时，重新提醒版本更新
            getPref().commitString(R.string.last_remind_update_version, "");
        }
        presenter.getVersionInfo();
    }

    /**
     * ########################################################Override方法##############################################################
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /***
         * 防止app被系统回收造成现场恢复数据不正常
         */
        if (AppGCManager.getGCStatus()) {
            AppGCManager.setGCStatus(false);
            AppGCManager.restartApp(this);
            this.finish();
            return;
        }
        getPref().commitBoolean(R.string.is_app_active, true);
        setContentView(R.layout.activity_main);

        presenter = new HomeActivityPresenter(this);//此处使用MVP设计模式，属于P
        presenter.handleCitySelected();

        //初始化视图
        intHomeActivityView();

     /*   presenter.processChangeTabBarIcon(
                findViewById(R.id.tab_user),
                findViewById(R.id.tab_record),
                findViewById(R.id.tab_discovery),
                findViewById(R.id.tab_mine));*/

        //startDLNAService();//开启服务查找DLNA设备？不知是何用
        setEventBusEnable(true);//开启eventbus
       // handleEvokeArg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != getSupportFragmentManager().findFragmentByTag("FORCE_UPDATE")) return;
        if (null != getSupportFragmentManager().findFragmentByTag("UPDATE")) return;
        //checkNewVersion(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(MainTab.TAB_USER_TAG);
        if (fragmentByTag != null && fragmentByTag instanceof LFUserMainFragment) {
            ((LFUserMainFragment) fragmentByTag).onWindowFocusChanged(hasFocus);
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
       /* Fragment fragment = getTabFragment(mTabHost.getCurrentTabTag());
        if (Plugs.getInstance().createH5Plug().execIsCanBackByUserH5Fragment(fragment)) {
            Plugs.getInstance().createH5Plug().execGoBackByUserH5Fragment(fragment);
            return;
        }*/
        //退出程序
        if (System.currentTimeMillis() - LastBackTime > 2500) {
            LastBackTime = System.currentTimeMillis();
            if (mToast == null) mToast = Toast.makeText(this, "再点一次退出应用", Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast.cancel();
            quitApp();
        }
    }

    @Override
    protected void onDestroy() {
        getPref().applyBoolean(R.string.is_app_active, false);
       // stopDLNAService();
        unRegisterContentObserver();
        super.onDestroy();
    }

    /**
     * #####################################################私有方法#################################################
     */

    /***
     * 处理唤醒或者恢复APP传递进来的参数
     * 应用场景：a.当app页面处于后台或者由于内存补足被系统回收或者因为bug导致app崩掉，app会保存当时的页面数据，当用户重新重启app时，
     *          打开LFUserHomeActivity时，根据保存的数据跳转到对应的页面。
     *          b.当app处于后台，收到推送时，用户点击通知栏按钮，跳转到相应页面。
     *
     */
    /*private void handleEvokeArg() {
        if (null != getIntent().getExtras()) {
            Bundle bundle = getIntent().getExtras();
            int evokeType = bundle.getInt(EvokeArg.KEY_EVOKE_TYPE, -1);
            if (TextUtils.isEmpty(bundle.getString(EvokeArg.KEY_EVOKE_VALUE, ""))) return;
            if (evokeType == EvokeArg.EVOKE_TYPE_EXTERNAL_CALL) {//从其他页面跳转过来
                jumpToTabPage(MainTab.TAB_USER_TAG);
                String evokeArg = bundle.getString(EvokeArg.KEY_EVOKE_VALUE, "");
                if (!LFAppConfigOps.isProduction()) FileUtil.writeEvokeInfoToFile(evokeArg);//当前不是开发版本时，将evokeArg写入文件
                ExternalEvoker.onExternalCallEvoke(this, evokeArg);
            } else if (evokeType == EvokeArg.EVOKE_TYPE_PUSH) {//从推送跳转过来
                jumpToTabPage(MainTab.TAB_USER_TAG);
                String pushArg = bundle.getString(EvokeArg.KEY_EVOKE_VALUE, "");
                if (!LFAppConfigOps.isProduction()) FileUtil.writePushInfoToFile(pushArg);//当前不是开发版本时，将evokeArg写入文件
                PushEvoker.onPushEvoke(this, pushArg);
            }
        }
    }*/

    /**
     * 初始化View TabHost
     */
    private void intHomeActivityView() {
        Fragment h5Fragment = Plugs.getInstance().createH5Plug().getUserH5Fragment();

        myDotView = findViewById(R.id.my_red_dot);
        findDotView = findViewById(R.id.find_red_dot);
        mTabHost = (LFFragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setUp(this, getSupportFragmentManager(), R.id.tab_index, R.id.tab_content);
        findViewById(R.id.tab_user).setOnClickListener(mOnTabClickListener);
        findViewById(R.id.tab_record).setOnClickListener(mOnTabClickListener);
        findViewById(R.id.tab_discovery).setOnClickListener(mOnTabClickListener);
        findViewById(R.id.tab_mine).setOnClickListener(mOnTabClickListener);

        mTabHost.addTab(MainTab.TAB_USER_TAG, LFUserMainFragment.class, new Bundle(), R.id.tab_user);
        mTabHost.addTab(MainTab.TAB_RECORD_TAG, LFRecordFragment.class, new Bundle(), R.id.tab_record);
        mTabHost.addTab(MainTab.TAB_DISCOVERY_TAG, h5Fragment.getClass(), h5Fragment.getArguments(), R.id.tab_discovery);
        mTabHost.addTab(MainTab.TAB_MINE_TAG, MineFragment.class, new Bundle(), R.id.tab_mine);

        mTabHost.setCurrentTabByTag(MainTab.TAB_USER_TAG);
    }

    /**
     * 处理异地登录
     */
    private void handleTokenInvalid(String msg) {

    }

   /* private void startDLNAService() {
        DLNAContainer.getInstance().clear();
        Intent intent = new Intent(getApplicationContext(), DLNAService.class);
        startService(intent);
    }*/

/*    private void stopDLNAService() {
        Intent intent = new Intent(getApplicationContext(), DLNAService.class);
        stopService(intent);
    }*/

    /**
     * 取消监听升级进度条
     **/
    private void unRegisterContentObserver() {
        if (null != DownloaderUtil.getInstance().getDownloadObserver()) {
            getContentResolver().unregisterContentObserver(
                    DownloaderUtil.getInstance().getDownloadObserver());
        }
    }

    /**
     * 退出app
     */
    private void quitApp() {
        //将上一次提醒更新的版本置空，每次重新开始app时，重新提醒版本更新
        getPref().commitString(R.string.last_remind_update_version, "");
        finish();
    }

    /**
     * 显示强制更新的提示框
     */
    private void showForceUpdateDialog() {

    }

    @SuppressWarnings("unused")
    public void onEvent(MyEvent.CommonEvent evt) {
        switch (evt) {
            case Event_DownLoad_progress:
                if (null != mProgressBar && null != progressbar_download) {
                    if (evt.getObject() instanceof Integer) {
                        int progressTemp = (Integer) evt.getObject();
                        if (progressTemp <= 0) progressTemp = 0;
                        if (progressTemp >= 100) progressTemp = 100;
                        mainUiHandler.sendMessage(mainUiHandler.obtainMessage(101, progressTemp));
                    }
                }
                break;
            default:
                break;
        }
    }

    private Handler mainUiHandler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progressTemp = (int) msg.obj;
            mProgressBar.setProgress(progressTemp);
            progressbar_download.setText("悟空升级中... " + progressTemp + "%");
            if (progressTemp >= 100) {
                ToastUtil.show(LFUserHomeActivity.this, "下载完成");
                quitApp();
            }
        }
    };

    @SuppressWarnings("unchecked")
    private <T extends Fragment> T getTabFragment(String tabTag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tabTag);
    }
}


