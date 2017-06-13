package com.jaryjun.common_base.ops;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.user.LFApplication;
import com.jaryjun.common_base.user.LFBaseActivity;
import com.jaryjun.common_base.utils.DeviceUtil;


/**
 * Created by Ted on 2015/5/21.
 */
public class LFUiOps {

  public static int getScreenHeight(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (null != context && context instanceof LFBaseActivity) {
      ((LFBaseActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      WindowManager wm =
          (WindowManager) LFApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
      wm.getDefaultDisplay().getMetrics(dm);
    }
    int screenHeight = dm.heightPixels;
    return screenHeight;
  }

  public static int getScreenWidth(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (null != context && context instanceof LFBaseActivity) {
      ((LFBaseActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      WindowManager wm =
          (WindowManager) LFApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
      wm.getDefaultDisplay().getMetrics(dm);
    }
    int screenHeight = dm.widthPixels;
    return screenHeight;
  }

  public static int getScreenWidth() {
    return LFApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
  }

  public static int getScreenHeight() {
    return LFApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
  }

  public static int getStatusBarHeight(Context context) {
    int result = 0;
    try {
      int resourceId =
          context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
        result = context.getResources().getDimensionPixelSize(resourceId);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
    return result;
  }

  private int getNavigationBarHeight(Context context) {
    int height = 0;
    try {
      Resources resources = context.getResources();
      int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
      height = resources.getDimensionPixelSize(resourceId);
    } catch (Exception ex) {
      //获取不到，默认0
    }
    return height;
  }

  /**
   * 获取屏幕分辨率
   */
  public static int[] getScreenSize(Resources resources) {
    return DeviceUtil.getScreenSize(resources);
  }

  public static int dip2px(float dipValue) {
    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
        LFApplication.getInstance().getResources().getDisplayMetrics()) + 0.5f);
  }

  public static int dip2px(Context context, float dipValue) {
    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
        context.getResources().getDisplayMetrics()) + 0.5f);
  }

  public static int px2sp(float pxValue) {
    final float fontScale =
        LFApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
  }

  @SuppressWarnings("Deprecated")
  public static void setTextViewBg(TextView textView, Drawable drawable) {
    if (null == textView || null == drawable) return;
    if (Build.VERSION.SDK_INT > 15) {
      textView.setBackground(drawable);
    } else {
      textView.setBackgroundDrawable(drawable);
    }
  }

  public static int[] getSchemesColors(Context context) {
    int[] colorSchemeColors = new int[] {
        context.getResources().getColor(R.color.app_color_blue),
        context.getResources().getColor(android.R.color.holo_green_light),
        context.getResources().getColor(android.R.color.holo_orange_light),
        context.getResources().getColor(R.color.app_color_blue)
    };
    return colorSchemeColors;
  }

  /***
   * 是否支持透明状态栏
   */
  public static boolean isSupportTranslucentStatus(Context context) {
    return getStatusBarHeight(context) > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  public static boolean checkDeviceHasNavigationBar(Context context) {

    //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
    boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
    boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

    if (!hasMenuKey && !hasBackKey) {
      // 这个设备有一个导航栏,但无法确认该导航栏是隐藏还是展开
      return true;
    }
    return false;
  }
}
