package com.junwu.plugin.empty;

import android.content.Context;

import com.junwu.plugin.plugin.RentPlugin;


/**
 * Created by Glen on 2016/6/14.
 * To modify the relevant code, please contact the developer Glen
 */
public class RentEmpty implements RentPlugin {
    Context context;

    @Override
    public void init(Context context) {

    }

    /*@Override
    public void startRentListActivity(Context context, SMapListParamsModel model, int code) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public LFBaseServiceFragment getRentRecordFragment() {
        return new EmptyFragment();
    }

    @Override
    public LFBaseServiceFragment getRentCollectFragment() {
        return null;
    }

    @Override
    public void deleteCollectFromDetail() {}

    public void openRentHouseDetail(Context context, int houseId) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public LFBaseFragment getMapDetailFragment(int businessId, String tag, Bundle bundle) {
        return null;
    }

    @Override
    public void executeReloadDataByMapDetailFragment(Fragment fragment, Bundle bundle) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void executeCloseByMapDetailFragment(Fragment fragment, boolean animation) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void refreshRentRecordFragment(LFBaseServiceFragment fragment) {
        Util.unInstallPlugin(this.context);
    }*/
}
