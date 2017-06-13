package com.jaryjun.common_base.utils;

/**
 * Created by Ted on 2015/5/21.
 * SystemUtil
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 系统功能调用工具类
 *
 * @author ted
 */
public final class SystemUtil {

  /**
   * 调用系统拨号功能，跳到拨号界面
   *
   * @param phoneNumber 电话号码
   * @param extensionNumber 分机号码 没有就传空
   * @throws Exception 异常
   */
//  public static void callPhone(Context context, String phoneNumber, String extensionNumber)
//      throws Exception {
//    try {
//      Uri phone;
//      if (TextUtils.isEmpty(extensionNumber)) {
//        phone = Uri.parse("tel:" + phoneNumber);
//      } else {
//        phone = Uri.parse("tel:" + phoneNumber + PhoneNumberUtils.PAUSE + extensionNumber);
//      }
//      Intent intent = new Intent(Intent.ACTION_CALL, phone);
//      context.startActivity(intent);
//    } catch (Exception ex) {
//      throw new Exception("can not make call!!!!!!!!!");
//    }
//  }

  /**
   * 显示或隐藏软键盘， 若软键盘处于显示状态，则执行隐藏； 若软键盘处于隐藏状态,则执行显示
   */
  public static void showOrHideSoftInput(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public static void hideSoftInput(Activity activity) {
    if (null == activity || activity.getCurrentFocus() == null) {
      return;
    }
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public static void hideSoftInput(Activity activity, View focusView) {
    if (null == focusView || null == activity) {
      return;
    }
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public static void showSoftInputForce(Activity activity, View focusView) {
    if (null == focusView || null == activity || !(focusView instanceof EditText)) {
      return;
    }
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  /**
   * 获取软键盘的打开状态
   *
   * @return true, 打开状态；false,关闭状态
   */
  public static boolean isSoftInputActive(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    return imm.isActive();// isOpen若返回true，则表示输入法打开
  }

  /***
   * 是否是酷派机型
   */
  public static boolean isCoolpad() {
    try {
      String lowerCaseModel = Build.MODEL.toLowerCase();
      return lowerCaseModel.contains("coolpad") || lowerCaseModel.contains("dazen");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isBlur() {
    try {
      return Build.BRAND.toLowerCase().contains("blur");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是中兴手机
   */
  public static boolean isZTE() {
    try {
      return Build.BRAND.toLowerCase().contains("zte");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是三星S2
   */
  public static boolean isGalaxyS2() {
    try {
      String lowerCaseModel = Build.MODEL.toLowerCase();
      return lowerCaseModel.contains("gt-i9100")
          || lowerCaseModel.contains("gt-i9108")
          || lowerCaseModel.contains("gt-i9103");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是魅族手机
   */
  public static boolean isMX() {
    try {
      String lowerCaseModel = Build.MODEL.toLowerCase();
      return lowerCaseModel.contains("m353") || lowerCaseModel.contains("mx");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是Nexus手机
   */
  public static boolean isNexusS() {
    try {
      String lowerCaseModel = Build.MODEL.toLowerCase();
      return lowerCaseModel.contains("nexus s");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是亚马逊的kindle fire
   */
  public static boolean isKindleFire() {
    try {
      return Build.MODEL.toLowerCase().contains("kindle fire");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 是否是小米手机
   */
  public static boolean isMIUI() {
    File buildPropFile = new File("/system/build.prop");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(buildPropFile));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("ro.miui.ui.version.code")) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 判断sdk版本是否在制定版本之上
   */
  public static boolean aboveApiLevel(int sdkInt) {
    return getApiLevel() >= sdkInt;
  }

  /**
   * 获取sdk版本
   */
  public static int getApiLevel() {
    return Build.VERSION.SDK_INT;
  }

  /**
   * 是否是LOLLIPOP之上
   */
  public static boolean isLOLLIPOP() {
    return aboveApiLevel(Build.VERSION_CODES.LOLLIPOP);
  }

  /**
   * 是否挂在SD卡
   */
  public static boolean isSDCardMounted() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }

  /**
   * 是否挂在外部SD卡
   */
  public static boolean isExternalSDCardMounted() {
    if (Build.VERSION.SDK_INT < 11) {
      return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    } else {
      return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment
          .isExternalStorageEmulated();
    }
  }

  /**
   * 获取外部存储可用空间
   */
  public static long getAvailableExternalStorage() {
    try {
      File file = Environment.getExternalStorageDirectory();
      if (file != null && file.exists()) {
        StatFs sdFs = new StatFs(file.getPath());
        if (sdFs != null) {
          long sdBlockSize = sdFs.getBlockSize();
          long sdAvailCount = sdFs.getAvailableBlocks();
          return sdAvailCount * sdBlockSize;
        }
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 获取外部存储总大小
   */
  public static long getTotalExternalMemorySize() {
    try {
      File file = Environment.getExternalStorageDirectory();
      if (file != null && file.exists()) {
        StatFs sdFs = new StatFs(file.getPath());
        if (sdFs != null) {
          long sdBlockSize = sdFs.getBlockSize();
          long sdTotalCount = sdFs.getBlockCount();
          return sdTotalCount * sdBlockSize;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * 获取内部存储可用空间
   */
  public static long getAvailableInternalStorage() {
    File file = Environment.getDataDirectory();
    if (file != null && file.exists()) {
      StatFs sdFs = new StatFs(file.getPath());
      if (sdFs != null) {
        long sdBlockSize = sdFs.getBlockSize();
        long sdAvailCount = sdFs.getAvailableBlocks();
        return sdAvailCount * sdBlockSize;
      }
    }
    return 0;
  }

  /**
   * 获取内部存储总大小
   */
  public static long getTotalInternalMemorySize() {
    File path = Environment.getDataDirectory();
    if (path != null && path.exists()) {
      StatFs stat = new StatFs(path.getPath());
      long blockSize = stat.getBlockSize();
      long totalBlocks = stat.getBlockCount();
      return totalBlocks * blockSize;
    }
    return 0;
  }

  /**
   * 检查内部存储是否够用
   */
  public static boolean checkAvailableInternalStorage(long size) {
    long availabelStorage = SystemUtil.getAvailableInternalStorage();
    if (size < 0) {
      return true;
    }
    if (availabelStorage <= 0) {
      return false;
    }
    return availabelStorage >= size;
  }

  /**
   * 检查外部存储是否够用
   */
  public static boolean checkAvailableExternalStorage(long size) {
    long availabelStorage = SystemUtil.getAvailableExternalStorage();
    if (size < 0) {
      return true;
    }
    if (availabelStorage <= 0) {
      return false;
    }
    return availabelStorage >= size;
  }

  /**
   * 获取设备的imei号
   */
  public static String getImei(Context context) {
    try {
      TelephonyManager telephonyManager =
          (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return telephonyManager.getDeviceId();
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * 检查设备是否root
   */
  public static boolean isRooted() {
    boolean rooted = false;
    boolean hasSuFile = false;
    String command = "ls -l /%s/su";
    File su = new File("/system/bin/su");
    if (su.exists()) {
      hasSuFile = true;
    } else {
      su = new File("/system/xbin/su");
      if (su.exists()) {
        hasSuFile = true;
      } else {
        su = new File("/data/bin/su");
        if (su.exists()) {
          hasSuFile = true;
        }
      }
    }
    if (hasSuFile == true) {
      rooted = true;
    }
    return rooted;
  }

  public static String getVersionName(Context context) {
    String VERSION = "1.5.0";
    try {
      PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      VERSION = pi.versionName;
      return VERSION;
    } catch (PackageManager.NameNotFoundException e) {
      return VERSION;
    }
  }
}
