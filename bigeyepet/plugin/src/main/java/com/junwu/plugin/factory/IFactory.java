package com.junwu.plugin.factory;


import com.junwu.plugin.plugin.H5Plugin;
import com.junwu.plugin.plugin.LandlordPlugin;
import com.junwu.plugin.plugin.MapPlugin;
import com.junwu.plugin.plugin.PayPlugin;
import com.junwu.plugin.plugin.RentPlugin;
import com.junwu.plugin.plugin.UserPlugin;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public interface IFactory {
    UserPlugin createUserPlug();

    LandlordPlugin createLandLordPlug();

    H5Plugin createH5Plug();

    MapPlugin createMapPlug();

    PayPlugin createPayPlug();

    RentPlugin createRentPlug();
}
