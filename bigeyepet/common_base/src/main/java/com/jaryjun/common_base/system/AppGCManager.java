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

package com.jaryjun.common_base.system;

import android.content.Context;
import android.content.Intent;

import com.jaryjun.common_base.user.LFApplication;


/**
 * Created by Ted on 2016/5/11.
 *
 * @ com.wukong.base.common.system
 */
public class AppGCManager {

  public static void setGCStatus(boolean isGC) {
    LFApplication.getInstance().getPref().commitBoolean("android_gc_status", isGC);
  }

  public static boolean getGCStatus() {
    return LFApplication.getInstance().getPref().getBoolean("android_gc_status", false);
  }

  public static void restartApp(Context context){
    Intent startIntent = new Intent();
    startIntent.setClassName("com.wukong.ua", "com.wukong.user.home.LFUserTransferActivity");
    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startIntent.putExtra("transfer_key", 0x010);
    context.startActivity(startIntent);
  }
}
