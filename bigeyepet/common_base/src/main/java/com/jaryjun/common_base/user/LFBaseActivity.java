/*******************************************************************************
 * * Copyright (C) 2015 www.wkzf.com
 * *
 * * Licensed under the Apache License, Version 2.0 (the "License");
 * * you may not use this file except in compliance with the License.
 * * You may obtain a copy of the License at
 * *
 * *      http://www.apache.org/licenses/LICENSE-2.0
 * *
 * * Unless required by applicable law or agreed to in writing, software
 * * distributed under the License is distributed on an "AS IS" BASIS,
 * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * * See the License for the specific language governing permissions and
 * * limitations under the License.
 ******************************************************************************/

package com.jaryjun.common_base.user;

import android.content.Context;

import com.jaryjun.common_base.utils.PreferenceUtil;
import com.jaryjun.common_base.utils.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by youj on 2015/4/13.
 * Activity 基类
 */
public class LFBaseActivity extends WActivity {
  protected final int MAKE_CALL_FAIL_MSG = 0x1110;





  @Override
  protected void onDestroy() {
    setEventBusEnable(false);
    super.onDestroy();
  }

  /**
   * 开关EventBus总线
   */
  protected void setEventBusEnable(boolean enable) {
    if (enable && !EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this);
    }
    if (!enable && EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().unregister(this);
    }
  }

  public PreferenceUtil getPref() {
    return LFApplication.getInstance().getPref();
  }

  public void show(Context context, String info) {
    ToastUtil.show(context, info);
  }




}
