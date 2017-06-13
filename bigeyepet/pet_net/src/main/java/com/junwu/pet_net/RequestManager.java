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

package com.junwu.pet_net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;


/**
 * Created by wangying on 2015/1/20.
 * RequestManager
 */
public class RequestManager {
  private static final int DEFAULT_DISK_USAGE_BYTES = 10 * 1024 * 1024;

  private static RequestManager mInstance;
  private RequestQueue mRequestQueue;
  private static Context mCtx;

  private RequestManager(Context context) {
    mCtx = context;
    mRequestQueue = getRequestQueue();
  }

  public static synchronized RequestManager getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new RequestManager(context);
    }
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      // getApplicationContext() is key, it keeps you from leaking the
      // Activity or BroadcastReceiver if someone passes one in.
      mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HurlStack(),
          DEFAULT_DISK_USAGE_BYTES);
    }
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    getRequestQueue().add(req);
  }

  /**
   * 清除缓存
   *
   * @param key key
   */
  public void clearRequestCache(String key) {
    if (key == null) return;
    getRequestQueue().getCache().remove(key);
  }
}
