package com.junwu.plugin.plugin;

/**
 * Created by Glen on 2016/6/12.
 * To modify the relevant code, please contact the developer Glen
 */
public interface MapPlugin extends BasePlugin {
    /**
     * 开始定位
     */
    //void startLocation(SMapLocationCallback callback);

    /**
     * 读取缓存定位结果
     */
   // SMapLocationModel getCacheLocation();

    /**
     * 初始化二手房地图配置
     */
    void initOwnedMapConfig(float district_start, float plat_start, float plot_level);

    /**
     * 初始化租房地图配置
     */
    void initRentMapConfig(float district_start, float plat_start, float plot_level);

    /**
     * 初始化新房地图配置
     */
    void initNewMapConfig(float district_start, float building_start);

    /**
     * 打开房源地图页面（二手房，新房，租房，详见model定义）
     */
   // void startHouseMapActivity(Context context, CityModel city, SMapIRModel model);

    /**
     * 周边
     */
   // void startAroundMapActivity(Context context, String cityName, Coordinate coordinate, boolean mIsForeign);
}
