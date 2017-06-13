package com.junwu.plugin.plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.PopupWindow;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public interface UserPlugin extends BasePlugin {
    /**
     * 处理登录
     */
    void login(Context context, int requestCode);

    /**
     * 设置发现小红点状态
     */
    void setFindIconIsShow(Context context, boolean flag);

    /**
     * 新房房源详情页
     */
    void openNewHouseDetail(Context context, int houseId);

    /**
     * 二手房源详情页
     */
    void openOwnerHouseDetail(Context context, long houseId, int systemType);

    /**
     * 小区目前在售
     */
    void openSameSaleHouse(Context context, @NonNull String subEstateId);

    /**
     * 小区历史成交
     */
    void openHistoryTurnover(Context context, @NonNull String subEstateId, @Nullable String estateName);

    /**
     * 跳转经济人评价页
     */
    void openEvaluateAgentActivity(Context context, int agent_id, int systemAgentType);

    /**
     * 跳转到聊天页面
     */
    void openIMChat(Context context, String toChatName);

    /**
     * 跳转到小区评论
     */

    void openAreaComment(Context context, String mEId, String eName, boolean IsScrollBottom);

    /**
     * 打开看房记录页面
     */
    void openRecordPage(Context context, int businessType);

    /**
     * 打开分享页面
     */
    void openSharedPage(Context context, String title, String image, String url, String content);

    /**
     * 进入系统消息
     */
    void goToConversationList(Context context);

    /**
     * 进入系统消息
     */
    void goToConversationList(Context context, Bundle bundle);

    /**
     * 打开搜索页面
     */
   // void openSearchActivity(Context context, SSearchIntentModel intentModel, int requestCode);

    /**
     * 气泡房源详情
     */
    //LFBaseFragment getMapDetailFragment(int businessId, String tag, Bundle bundle);

    /**
     * Reload气泡房源详情
     */
    void executeReloadDataByMapDetailFragment(Fragment fragment, Bundle bundle);

    /**
     * Close气泡房源详情
     */
    void executeCloseByMapDetailFragment(Fragment fragment, boolean animation);

    /**
     * 获取小区评论fragment
     */
   // LFBaseFragment getCommentShowFragment();

    /**
     * 小区评论fragment填充数据
     */
   // void loadDateByCommentShowFragment(LFBaseFragment fragment, String id);

    /**
     * 获取InputPopupWindow
     */
    PopupWindow getInputPopupWindow(Activity activity);

    /**
     * 打开二手房列表
     */
 //   void startOwnedHouseListActivity(Context context, SMapListParamsModel model, int code);

    /**
     * 打开新房列表
     */
  //  void startNewHouseListActivity(Context context, SMapListParamsModel model, int code);

    /**
     * 是否显示未读消息，是否显示小红点，就看这.此处的消息，包括 IM 消息和小米推送的消息
     */
    boolean hasUnReadMsg();

    /**
     * 去具体的聊天会话界面
     */
  //  void goToChatAct(Context context, @NonNull ImAgentModel agentModel);

    /**
     * 跳转到LFUserHomeActivity
     */
    void openUserHomeActivity(Context context, int homeActionType);

    /**
     * 跳转收藏页面
     * select：打开搜藏页时选中的页面，0：二手房，1：新房，2：租房
     */
    void openCollectActivity(Context context, int select);

    int getRequestCode();
}