package com.junwu.plugin.empty;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.PopupWindow;

import com.junwu.plugin.factory.Util;
import com.junwu.plugin.plugin.UserPlugin;


/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public class UserEmpty implements UserPlugin {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void login(Context context, int requestCode) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void setFindIconIsShow(Context context, boolean flag) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openNewHouseDetail(Context context, int houseId) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openOwnerHouseDetail(Context context, long houseId, int systemType) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openSameSaleHouse(Context context, @NonNull String subEstateId) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openHistoryTurnover(Context context, @NonNull String subEstateId, @Nullable String estateName) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openEvaluateAgentActivity(Context context, int agent_id, int systemAgentType) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openIMChat(Context context, String toChatName) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openAreaComment(Context context, String mEId, String eName, boolean IsScrollBottom) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openRecordPage(Context context, int businessType) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openSharedPage(Context context, String title, String image, String url, String content) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void goToConversationList(Context context) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void goToConversationList(Context context, Bundle bundle) {

    }

  /*  @Override
    public void openSearchActivity(Context context, SSearchIntentModel intentModel, int requestCode) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public LFBaseFragment getMapDetailFragment(int businessId, String tag, Bundle bundle) {
        return new EmptyFragment();
    }
*/
    @Override
    public void executeReloadDataByMapDetailFragment(Fragment fragment, Bundle bundle) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void executeCloseByMapDetailFragment(Fragment fragment, boolean animation) {
        Util.unInstallPlugin(this.context);
    }

   /* @Override
    public LFBaseFragment getCommentShowFragment() {
        return new EmptyFragment();
    }

    @Override
    public void loadDateByCommentShowFragment(LFBaseFragment fragment, String id) {
        Util.unInstallPlugin(this.context);
    }*/

    @Override
    public PopupWindow getInputPopupWindow(Activity activity) {
        return null;
    }

   /* @Override
    public void startOwnedHouseListActivity(Context context, SMapListParamsModel model, int code) {
        Util.unInstallPlugin(this.context);
    }*/

    @Override
    public boolean hasUnReadMsg() {
        return false;
    }

   /* @Override
    public void startNewHouseListActivity(Context context, SMapListParamsModel model, int code) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void goToChatAct(Context context, @NonNull ImAgentModel agentModel) {
        Util.unInstallPlugin(this.context);
    }*/

    @Override
    public void openUserHomeActivity(Context context, int homeActionType) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void openCollectActivity(Context context, int select) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public int getRequestCode() {
        return 0;
    }
}
