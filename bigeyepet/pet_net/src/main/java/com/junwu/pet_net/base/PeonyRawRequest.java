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

package com.junwu.pet_net.base;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junwu.pet_net.utils.NetWorkLog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangying on 9/1/15.
 * PeonyRawRequest
 */
public class PeonyRawRequest<T> extends Request<T> {
  //public final static String TAG = PeonyRawRequest.class.getSimpleName();
  public final static String HEADER_SECRET = "App-Secret";
  //public final static int MAX_BODY_LENGTH = 4 * 1024;
  public final static int MAX_BODY_VALUE_LENGTH = 128;

  protected ObjectMapper mMapper = new ObjectMapper();
  protected Response.Listener<T> listener;
  private Object mData;
  private HashMap<String, String> mHeaders;

  public PeonyRawRequest(int method, String url, Response.ErrorListener listener) {
    super(method, url, listener);
  }

  public PeonyRawRequest(String url, HashMap<String, String> headers, Object data,
                         Response.Listener<T> listener, Response.ErrorListener volleyErrorListener) {
    super(Method.POST, url, volleyErrorListener);
    mData = data;
    mHeaders = headers;
    this.listener = listener;
  }

  @Override
  public Map<String, String> getHeaders() throws AuthFailureError {
    Map<String, String> headers = new HashMap<>();
    if (mHeaders != null) {
      for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        headers.put(key, value);
      }
    }
    headers.put(HEADER_SECRET, getSignature(mData));
    return headers;
  }

  @Override
  public String getBodyContentType() {
    return "application/json; charset=" + getParamsEncoding();
  }

  @Override
  public byte[] getBody() throws AuthFailureError {
    return mData.toString().getBytes();
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Response<T> parseNetworkResponse(NetworkResponse response) {
    try {
      String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
      return Response.success((T) json, HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException e) {
      return Response.error(new ParseError(e));
    }
  }

  @Override
  protected void deliverResponse(T response) {
    listener.onResponse(response);
  }

  @SuppressWarnings("unchecked")
  protected String getSignature(Object request) {
    String signature = "";
    try {
      Map<String, Object> requestBody;
      if (request instanceof String) {
        requestBody = mMapper.readValue((String) request, Map.class);
      } else if (request instanceof EndpointRequest) {
        requestBody = mMapper.readValue(mMapper.writeValueAsString(request), Map.class);
      } else {
        return null;
      }
      List<Map.Entry<String, Object>> params = new ArrayList<>(requestBody.entrySet());
      Collections.sort(params, new Comparator<Map.Entry<String, Object>>() {
        public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
          return o1.getKey().compareTo(o2.getKey());
        }
      });

      String secret = "";
      for (Map.Entry<String, Object> param : params) {
        String key = param.getKey();
        Object value = param.getValue();
        if (value == null) {
          value = "";
        }
        if (value instanceof List || value instanceof Map) {
          value = "";
        }

        if (value.toString().length() > MAX_BODY_VALUE_LENGTH) {
          value = "";
        }
        secret = secret + key + "=" + value + "&";
      }
      //if (secret.endsWith("&")) secret = secret.substring(0, secret.length() - 1);

     // String timestamp = String.valueOf(ServerTimeSync.getTimeInMillis() / 100000);

      //signature = Encrypt.decryptKey(secret, timestamp, NetworkManager.getPackageName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return signature;
  }

  public static void showJSON(boolean isShowJSON, String url, String tag, String message) {
    if (!isShowJSON) return;
    if (!TextUtils.isEmpty(url)) NetWorkLog.e("@@@@@@-url: " + url);
    if (TextUtils.isEmpty(tag)) tag = " ";
    NetWorkLog.e("@@@@@@- Begin " + tag + " Body -@@@@@@");
    NetWorkLog.e(message);
    NetWorkLog.e("@@@@@@- End " + tag + " Body -@@@@@@");
  }
}
