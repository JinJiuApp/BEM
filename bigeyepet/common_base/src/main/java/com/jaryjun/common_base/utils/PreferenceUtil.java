/*
 *
 * Copyright 2015 TedXiong xiong-wei@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jaryjun.common_base.utils;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.util.Map;

/**
 * This class helps in managing a SharedPreference with automatic use of resource id's,
 * and a quick support for apply/commit
 */
public class PreferenceUtil {

    private Resources mRes;
    private SharedPreferences mPref;

    public PreferenceUtil(Resources res, SharedPreferences preferences) {
        mRes = res;
        mPref = preferences;
    }
    public SharedPreferences getSharePreference(){
        return mPref;
    }

    public boolean isPrefContains(int resId) {
        return (mPref.contains(mRes.getString(resId)));
    }

    public String getString(int resId, String defValue) {
        return (mPref.getString(mRes.getString(resId), defValue));
    }

    public String getString(int resId){
        return getString(resId, null);
    }

    public void applyString(int resId, String value) {
        mPref.edit().putString(mRes.getString(resId), value).apply();
    }

    public void commitString(int resId, String value) {
        mPref.edit().putString(mRes.getString(resId), value).commit();
    }

    public boolean getBoolean(int resId, boolean defValue) {
        return (mPref.getBoolean(mRes.getString(resId), defValue));
    }

    public boolean getBoolean(String key, boolean defValue) {
        return (mPref.getBoolean(key, defValue));
    }

    public boolean getBoolean(int resId) {
        return getBoolean(resId, false);
    }

    public void applyBoolean(int resId, boolean value) {
        mPref.edit().putBoolean(mRes.getString(resId), value).apply();
    }

    public void commitBoolean(int resId, boolean value) {
        commitBoolean(mRes.getString(resId), value);
    }

    public void commitBoolean(String key, boolean value) {
        mPref.edit().putBoolean(key, value).commit();
    }

    public void applyBooleanReverseValue(int resId){
        applyBoolean(resId, !getBoolean(resId));
    }

    public void commitBooleanReverseValue(int resId){
        applyBoolean(resId, !getBoolean(resId));
    }

    public int getInt(int resId, int defValue) {
        return (mPref.getInt(mRes.getString(resId), defValue));
    }

    public int getInt(int resId) {
        return getInt(resId, Integer.MIN_VALUE);
    }

    public void applyInt(int resId, int value) {
        mPref.edit().putInt(mRes.getString(resId), value).apply();
    }

    public void commitInt(int resId, int value) {
        mPref.edit().putInt(mRes.getString(resId), value).commit();
    }

    public long getLong(int resId, long defValue) {
        return (mPref.getLong(mRes.getString(resId), defValue));
    }

    public long getLong(int resId) {
        return getLong(resId, Long.MIN_VALUE);
    }

    public void applyLong(int resId, long value) {
        mPref.edit().putLong(mRes.getString(resId), value).apply();
    }

    public void commitLong(int resId, long value) {
        mPref.edit().putLong(mRes.getString(resId), value).commit();
    }

    public float getFloat(int resId, float defValue) {
        return (mPref.getFloat(mRes.getString(resId), defValue));
    }

    public float getFloat(int resId) {
        return getFloat(resId, Float.MIN_VALUE);
    }

    public void applyFloat(int resId, float value) {
        mPref.edit().putFloat(mRes.getString(resId), value).apply();
    }

    public void commitFloat(int resId, float value) {
        mPref.edit().putFloat(mRes.getString(resId), value).commit();
    }

    public void commitMap(Map<String, Object> map){

        SharedPreferences.Editor edit = mPref.edit();

        runOverMap(map, edit);

        edit.commit();
    }

    private void runOverMap(Map<String, Object> map, SharedPreferences.Editor edit) {
        for (String key : map.keySet()) {
            Object value = map.get(key);

            if (value instanceof String){
                edit.putString(key, (String)value);
            }
            else if (value instanceof Boolean){
                edit.putBoolean(key, (Boolean) value);
            }
            else if (value instanceof Integer){
                edit.putInt(key, (Integer) value);
            }
            else if (value instanceof Float){
                edit.putFloat(key, (Float) value);
            }
            else if (value instanceof Double){
                edit.putFloat(key, (float)((double)((Double)value)));
            }
            else if (value instanceof Long){
                edit.putLong(key, (Long) value);
            }
            else if (value instanceof Map){
                // Recursively call next map
                runOverMap((Map)value, edit);
            }
            else{
                Log.e("SharedPrefUtil", "Trying to enter unkown type inside a shared pref map (type=" +
                        value.getClass().getSimpleName());
            }
        }
    }
}
