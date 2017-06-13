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
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.junwu.pet_net.base.EndpointRequest;
import com.junwu.pet_net.base.PeonyError;
import com.junwu.pet_net.base.PeonyRawRequest;
import com.junwu.pet_net.base.PeonyRequest;
import com.junwu.pet_net.listener.OnLoadCompleteListener;
import com.junwu.pet_net.listener.OnPeonyRetryListener;
import com.junwu.pet_net.listener.OnReceivedDataListener;
import com.junwu.pet_net.listener.OnReceivedErrorListener;
import com.junwu.pet_net.listener.OnRequestProcessLister;

import java.util.HashMap;

/**
 * Created by wangying on 2015/1/20.
 * NetworkManager
 */
public class NetworkManager {
  private Context mCtx;

  private static String mPackageName;
  private static NetworkManager mInstance;

  public static synchronized NetworkManager getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new NetworkManager(context);
    }
    return mInstance;
  }

  private NetworkManager(Context context) {
    mCtx = context;
    mPackageName = context.getApplicationContext().getPackageName();
  }

  public static String getPackageName() {
    return mPackageName;
  }

  //兼容房东服务，最终使用总服务基础入口
  public <T> void loadData(String path, Object requestTag, EndpointRequest request,
                           Class<T> responseClass, final OnReceivedDataListener<T> listener,
                           final OnReceivedErrorListener errorListener,
                           final OnLoadCompleteListener onLoadCompleteListener, OnRequestProcessLister processLister) {

    String url = request.getProtocol() + "://" + request.getHost() + ":" + request.getPort();
    if (!TextUtils.isEmpty(request.getPath())) {
      url = url + request.getPath() + "/" + path;
    } else {
      url = url + "/" + path;
    }

    loadData(url, String.valueOf(requestTag), false, null, 0, 0, request, responseClass,
        new Response.Listener<T>() {
          @Override
          public void onResponse(T response) {
            if (onLoadCompleteListener != null) {
              onLoadCompleteListener.onLoadComplete();
            }
            listener.onReceivedData(response);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            if (onLoadCompleteListener != null) {
              onLoadCompleteListener.onLoadComplete();
            }
            PeonyError peonyError = new PeonyError(error);
            if (error instanceof NoConnectionError) {
              peonyError.setErrorType(PeonyError.ERROR_NO_CONNECTION);
            } else if (error instanceof ServerError) {
              peonyError.setErrorType(PeonyError.ERROR_SERVER);
            } else if (error instanceof TimeoutError) {
              peonyError.setErrorType(PeonyError.ERROR_TIMEOUT);
            } else if (error instanceof ParseError) {
              peonyError.setErrorType(PeonyError.ERROR_PARSE);
            }
            errorListener.onReceivedError(peonyError);
          }
        }, processLister);
  }

  //兼容房东服务，最终使用总服务基础入口
  public <T> void loadData(String path, Object requestTag, EndpointRequest request,
                           Class<T> responseClass, final OnReceivedDataListener<T> listener,
                           final OnPeonyRetryListener onErrorRetryListener,
                           final OnLoadCompleteListener onLoadCompleteListener) {
    String url = request.getProtocol() + "://" + request.getHost() + ":" + request.getPort();
    if (!TextUtils.isEmpty(request.getPath())) {
      url = url + request.getPath() + "/" + path;
    } else {
      url = url + "/" + path;
    }

    loadData(url, String.valueOf(requestTag), false, null, 0, 0, request, responseClass,
        new Response.Listener<T>() {
          @Override
          public void onResponse(T response) {
            if (onLoadCompleteListener != null) {
              onLoadCompleteListener.onLoadComplete();
            }
            listener.onReceivedData(response);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            if (onLoadCompleteListener != null) {
              onLoadCompleteListener.onLoadComplete();
            }
            onErrorRetryListener.onErrorRetry();
          }
        }, null);
  }

  /**
   * 悟空找房app，总服务基础入口
   */
  public <T> void loadData(String url, String token, boolean cache, String cacheKey,
                           long cacheDuration, long refreshDuration, EndpointRequest request, Class<T> responseClass,
                           Response.Listener<T> successListener, Response.ErrorListener errorListener,
                           OnRequestProcessLister processLister) {
    PeonyRequest<T> peonyRequest =
        new PeonyRequest<>(url, request, responseClass, successListener, errorListener,
            processLister);
    peonyRequest.setTag(token);
    peonyRequest.setShouldCache(cache);
    peonyRequest.setCacheControl(cacheKey, cacheDuration, refreshDuration);
    peonyRequest.setCustomRetryPolicy(
        new DefaultRetryPolicy(3500, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestManager.getInstance(mCtx.getApplicationContext()).addToRequestQueue(peonyRequest);
  }

  /**
   * 仅H5支付使用
   */
  public <T> void loadData(String url, final Object requestTag, HashMap<String, String> headers,
                           final Object data, final OnReceivedDataListener<T> listener,
                           final OnReceivedErrorListener errorListener) {
    PeonyRawRequest<T> request =
        new PeonyRawRequest<>(url, headers, data, new Response.Listener<T>() {
          @Override
          public void onResponse(T response) {
            listener.onReceivedData(response);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            PeonyError peonyError = new PeonyError(error);
            if (error instanceof NoConnectionError) {
              peonyError.setErrorType(PeonyError.ERROR_NO_CONNECTION);
            } else if (error instanceof ServerError) {
              peonyError.setErrorType(PeonyError.ERROR_SERVER);
            } else if (error instanceof TimeoutError) {
              peonyError.setErrorType(PeonyError.ERROR_TIMEOUT);
            } else if (error instanceof ParseError) {
              peonyError.setErrorType(PeonyError.ERROR_PARSE);
            }
            errorListener.onReceivedError(peonyError);
          }
        });
    request.setTag(requestTag);
    request.setShouldCache(false);
    request.setRetryPolicy(
        new DefaultRetryPolicy(3500, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestManager.getInstance(mCtx.getApplicationContext()).addToRequestQueue(request);
  }

  public void cancel(Object requestTag) {
    RequestManager.getInstance(mCtx.getApplicationContext())
        .getRequestQueue()
        .cancelAll(requestTag);
  }
}