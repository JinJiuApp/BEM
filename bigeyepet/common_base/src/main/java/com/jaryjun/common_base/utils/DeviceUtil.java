package com.jaryjun.common_base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.user.LFApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by youj on 2015/4/13.
 * DeviceUtil
 */
public class DeviceUtil {
  /**
   * 获取屏幕分辨率
   */
  public static int[] getScreenSize(Resources resources) {
    int width = resources.getDisplayMetrics().widthPixels;
    int height = resources.getDisplayMetrics().heightPixels;
    int[] result = new int[2];
    result[0] = width;
    result[1] = height;
    return result;
  }

  /**
   * 获取用户的唯一身份识别
   *
   * @return 基于设备的唯一识别码，如果为空，就是是自定义唯一识别码
   */
 /* public static String getUserIdentity() {
    if (TextUtils.isEmpty(getDeviceIdentity(LFApplication.getInstance()))) {
      return getCustomIdentity();
    } else {
      return getDeviceIdentity(LFApplication.getInstance());
    }
  }*/

  /***
   * 获取设备相关唯一身份识别
   *
   * @param context context
   * @return 32位身份识别码或者空
   */
 /* public static String getDeviceIdentity(@NonNull Context context) {
    String complexStr = getMacAddress(context) + getDeviceId(context);
    return getMD5String(complexStr);
  }*/


  /***
   * 获取MuId，其实就是设备号，如果为空就传唯一身份识别
   *
   * @return String
   */
/*  public static String getMuId() {
    if(TextUtils.isEmpty(getDeviceId(LFApplication.getInstance()))){
      return getUserIdentity();
    }return getDeviceId(LFApplication.getInstance());
  }*/

  /***
   * 获取自定义识别码
   *
   * @return 32位身份识别码
   */
  public static String getCustomIdentity() {
    String identity =
        LFApplication.getInstance().getPref().getString(R.string.android_custom_identity, "");
    if (TextUtils.isEmpty(identity)) {
      String customStr = "custom_" + System.currentTimeMillis() + StringUtil.getRandomString(8);
      identity = getMD5String(customStr);
      LFApplication.getInstance()
          .getPref()
          .commitString(R.string.android_custom_identity, identity);
    }
    return identity;
  }


 /* public static String getMacAddress(@NonNull Context context) {
    WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    String macAddress = wm.getConnectionInfo().getMacAddress();
    if (TextUtils.isEmpty(macAddress) || "02:00:00:00:00:00".equals(macAddress)) {
      return "";
    }
    return macAddress;
  }*/

  public static String getDeviceId(@NonNull Context context) {
    TelephonyManager TelephonyMgr =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = TelephonyMgr.getDeviceId();
    return TextUtils.isEmpty(deviceId) ? "" : deviceId;
  }

  /***
   * 获取String的MD5值
   * @param content String内容
   * @return md5
   */
  public static String getMD5String(@NonNull String content) {
    MessageDigest m = null;
    try {
      m = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    if (null != m && !TextUtils.isEmpty(content)) {
      m.update(content.getBytes(), 0, content.length());
      byte p_md5Data[] = m.digest();
      String m_szUniqueID = "";
      for (byte aP_md5Data : p_md5Data) {
        int b = (0xFF & aP_md5Data);
        if (b <= 0xF) m_szUniqueID += "0";
        m_szUniqueID += Integer.toHexString(b);
      }
      return m_szUniqueID.toUpperCase();
    } else {
      return content;
    }
  }
}
