package com.junwu.user.business.home;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by Ted on 2016/2/18.
 *
 * @ com.wukong.user.business.home
 * 应用唤起参数类
 */
public class EvokeArg {
  /***
   * 说明，针对唤起应用的参数对象，目前仅仅传递两个参数，1，唤起类型（int类型） 2，相关参数（String类型）
   * 如果有其他参数需要传递，再增加即可
   */
  public static final String KEY_EVOKE_TYPE = "key_evoke_type";
  public static final String KEY_EVOKE_VALUE = "key_evoke_value";

  public static final int EVOKE_TYPE_PUSH = 0x001;//推送唤醒
  public static final int EVOKE_TYPE_EXTERNAL_CALL = 0x011;//外部调用，比如通过scheme

  private int evokeType = -1;
  private String evokeValue = null;

  public Bundle create(){
    Bundle bundle = new Bundle();
    if(evokeType > 0)bundle.putInt(KEY_EVOKE_TYPE,evokeType);
    if(!TextUtils.isEmpty(evokeValue))bundle.putString(KEY_EVOKE_VALUE,evokeValue);
    return bundle;
  }

  public EvokeArg setEvokeType(int evokeType) {
    this.evokeType = evokeType;
    return this;
  }

  public EvokeArg setEvokeValue(String evokeValue) {
    this.evokeValue = evokeValue;
    return this;
  }

}
