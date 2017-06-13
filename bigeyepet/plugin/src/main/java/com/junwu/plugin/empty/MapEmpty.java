package com.junwu.plugin.empty;

import android.content.Context;

import com.junwu.plugin.factory.Util;
import com.junwu.plugin.plugin.MapPlugin;


/**
 * Created by Glen on 2016/6/12.
 * To modify the relevant code, please contact the developer Glen
 */
public class MapEmpty implements MapPlugin {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

  /*  @Override
    public void startLocation(SMapLocationCallback callback) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public SMapLocationModel getCacheLocation() {
        Util.unInstallPlugin(this.context);
        return null;
    }*/

    @Override
    public void initOwnedMapConfig(float district_start, float plat_start, float plot_level) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void initRentMapConfig(float district_start, float plat_start, float plot_level) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void initNewMapConfig(float district_start, float building_start) {
        Util.unInstallPlugin(this.context);
    }

   /* @Override
    public void startHouseMapActivity(Context context, CityModel city, SMapIRModel model) {
        Util.unInstallPlugin(this.context);
    }

    @Override
    public void startAroundMapActivity(Context context, String cityName, Coordinate coordinate, boolean mIsForeign) {
        Util.unInstallPlugin(this.context);
    }*/
}
