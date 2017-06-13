package com.jaryjun.common_base.model;

import java.io.Serializable;

/**
 * Created by Qinbaoyuan on 2015/8/26.
 */
public class TransactionDetailModel implements Serializable {
    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    private String mContent;
    private String mTime;
}
