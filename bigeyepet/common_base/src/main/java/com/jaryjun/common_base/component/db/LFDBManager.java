package com.jaryjun.common_base.component.db;


import com.jaryjun.common_base.user.LFApplication;

import dao.AdModelDao;
import dao.BankItemDao;
import dao.BottomBarItemDao;
import dao.BusinessItemDao;
import dao.CityItemDao;
import dao.DaoMaster;
import dao.DaoSession;
import dao.SearchHistoryItemDao;

/**
 * Created by youj on 2015/5/16.
 */
public class LFDBManager {
    public static String DB_NAME = "DB_UA";
    private static LFDBManager instance;

    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper dbHelper;

    public static LFDBManager getIns() {
        if (null == instance) {
            synchronized (LFDBManager.class) {
                if (null == instance) {
                    instance = new LFDBManager();
                }
            }
        }
        return instance;
    }

    public void init() {
        dbHelper = new DaoMaster.DevOpenHelper(LFApplication.getInstance(), DB_NAME, null);
        daoSession = new DaoMaster(dbHelper.getWritableDatabase()).newSession();
    }

    public SearchHistoryItemDao getSearchHistoryDao() {
        return daoSession.getSearchHistoryItemDao();
    }

    public BankItemDao getBankItemDao() {
        return daoSession.getBankItemDao();
    }

    public AdModelDao getAdModelDao() {
        return daoSession.getAdModelDao();
    }

    public BottomBarItemDao getBottomBarDao() {
        return daoSession.getBottomBarItemDao();
    }

    public CityItemDao getCityItemDao() {
        return daoSession.getCityItemDao();
    }

    public BusinessItemDao getBusinessItemDao() {
        return daoSession.getBusinessItemDao();
    }
}
