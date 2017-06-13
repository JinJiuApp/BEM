package com.junwu.plugin.plugin;

/**
 * Created by Orochi on 2016/6/13.
 */
public interface RentPlugin extends BasePlugin {

   /* *//**
     * 租房列表
     * *//*
    void startRentListActivity(Context context, SMapListParamsModel model, int code);

    *//**
     * 获取租房记录实例
     *//*
    LFBaseServiceFragment getRentRecordFragment();

    *//**
     * 获取租房收藏实例
     *//*
    LFBaseServiceFragment getRentCollectFragment();

    *//**
     * 收藏页进详情，取消收藏，回到列表刷新页面
     *//*
    void deleteCollectFromDetail();
    *//**
     * 获取租房收藏实例
     *//*
    void openRentHouseDetail(Context context, int houseId);

    *//***
     *打开气泡房源
     *//*
    LFBaseFragment getMapDetailFragment(int businessId, String tag, Bundle bundle);

    *//**
     * 气泡列表重新读取
     *//*
    void executeReloadDataByMapDetailFragment(Fragment fragment, Bundle bundle);

    *//**
     * 气泡列表关闭
     *//*
    void executeCloseByMapDetailFragment(Fragment fragment, boolean animation);
    *//**
     * 刷新租房记录页数据
     *//*
    void refreshRentRecordFragment(LFBaseServiceFragment fragment);*/
}
