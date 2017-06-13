package com.junwu.user.business.model;

/**
 * Created by Ted on 2015/5/25.
 */
public class VersionInfoModel {

    public String version; //版本名称

    //public int versionCode; //版本号

    public String url; //下载地址

    public String message; //消息内容

    public int isForce; //是否强制更新0 非强制 1强制

    public String getVersionName() {
        return version;
    }

//    public int getVersionCode() {
//        return versionCode;
//    }

    public String getUrl() {
        return url;
    }

    public String getUpdateMessage() {
        return message;
    }

    public boolean getIsForce() {
        return isForce > 0;
    }
}
