package com.junwu.plugin.factory;

import android.content.res.XmlResourceParser;
import android.util.Log;

import com.jaryjun.common_base.user.LFApplication;
import com.junwu.plugin.R;
import com.junwu.plugin.empty.H5Empty;
import com.junwu.plugin.empty.LandlordEmpty;
import com.junwu.plugin.empty.MapEmpty;
import com.junwu.plugin.empty.PayEmpty;
import com.junwu.plugin.empty.RentEmpty;
import com.junwu.plugin.empty.UserEmpty;
import com.junwu.plugin.plugin.H5Plugin;
import com.junwu.plugin.plugin.LandlordPlugin;
import com.junwu.plugin.plugin.MapPlugin;
import com.junwu.plugin.plugin.PayPlugin;
import com.junwu.plugin.plugin.RentPlugin;
import com.junwu.plugin.plugin.UserPlugin;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public class PlugFactory implements IFactory {
    Map<String, Class<?>> plugs;
    Map<String, Object> instances;

    private static final String TAG = "PlugFactory";
    private static final String PLUGIN_USER = "user";
    private static final String PLUGIN_LANDLORD = "landlord";
    private static final String PLUGIN_H5 = "h5";
    private static final String PLUGIN_MAP = "map";
    private static final String PLUGIN_PAY = "pay";
    private static final String PLUGIN_RENT = "rent";

    protected PlugFactory() {
        try {
            loadPlug();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 产生对应插件UserPlugin，未安装插件则使用默认空插件UserEmpty
     * @return
     */
    @Override
    public UserPlugin createUserPlug() {return getInstance(PLUGIN_USER, UserEmpty.class);}

    @Override
    public LandlordPlugin createLandLordPlug() {return getInstance(PLUGIN_LANDLORD, LandlordEmpty.class);}

    @Override
    public H5Plugin createH5Plug() {
        return getInstance(PLUGIN_H5, H5Empty.class);
    }

    @Override
    public MapPlugin createMapPlug() {
        return getInstance(PLUGIN_MAP, MapEmpty.class);
    }

    @Override
    public PayPlugin createPayPlug() {
        return getInstance(PLUGIN_PAY, PayEmpty.class);
    }

    @Override
    public RentPlugin createRentPlug() {
        return getInstance(PLUGIN_RENT, RentEmpty.class);
    }

    private <T> T getInstance(String name, Class<T> clazz) {
        Object soft = instances.get(name);
        if (soft != null) {
            return (T) soft;
        }

        Class<?> plugClass = plugs.get(name);
        Object object = null;
        try {
            object = plugClass != null ? plugClass.newInstance() : clazz.newInstance();//如果没有name类则使用empty类
            Log.d(TAG, "create plugin instance success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (object != null) {
            instances.put(name, object);
        }
        return (T) object;
    }

    /**
     * xml解析，通过反射将类对应类名存到集合plugs中
     * @throws Exception
     */

    private void loadPlug() throws Exception {
        plugs = new HashMap<>();
        instances = new HashMap<>();
        //xml解析
        XmlResourceParser parser = LFApplication.getInstance().getResources().getXml(R.xml.plugin);

        String name = null;
        Class<?> clazz = null;
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            try {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Plug".equalsIgnoreCase(tagName)) {
                            name = parser.getAttributeValue(null, "name");
                            clazz = Class.forName(parser.getAttributeValue(null, "class"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("Plug".equalsIgnoreCase(tagName)) {
                            if (name != null && clazz != null) {
                                plugs.put(name, clazz);
                                name = null;
                                clazz = null;
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                name = null;
                clazz = null;
                e.printStackTrace();
            } finally {
                eventType = parser.next();
            }
        }
    }
}