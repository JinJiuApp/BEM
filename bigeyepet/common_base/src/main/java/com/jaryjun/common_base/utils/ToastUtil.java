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

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaryjun.common_base.R;


/**
 * Created by Ted on 2015/2/6.
 * ToastUtil
 */
public class ToastUtil {

  public static void show(Context context, int infoId) {
    show(context, infoId, Toast.LENGTH_SHORT, Gravity.BOTTOM);
  }

  public static void show(Context context, String info) {
    show(context, info, Toast.LENGTH_SHORT, Gravity.BOTTOM);
  }

  public static void showCenter(Context context, int infoId) {
    show(context, infoId, Toast.LENGTH_SHORT, Gravity.CENTER);
  }

  public static void showCenter(Context context, String info) {
    show(context, info, Toast.LENGTH_SHORT, Gravity.CENTER);
  }

  public static void show(Context context, String info, int duration, int gravity) {
    Toast toast = new Toast(context);
    toast.setDuration(duration);
    View mToastView = View.inflate(context, R.layout.base_main_toast_layout, null);
    ((TextView) mToastView.findViewById(R.id.toast_txt)).setText(info);
    toast.setView(mToastView);
    if (gravity == Gravity.BOTTOM) {
      toast.setGravity(gravity, 0, 200);
    } else {
      toast.setGravity(gravity, 0, 0);
    }

    toast.show();
  }

  public static void show(Context context, int infoId, int duration, int gravity) {
    show(context, context.getResources().getString(infoId), duration, gravity);
  }
}
