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

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.junwu.pet_net.annotation.JsonDebugAnnotation;
import com.junwu.pet_net.listener.OnRequestProcessLister;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Custom Request
 */
public class PeonyRequest<T> extends PeonyRawRequest<T> {
    public final static String TAG = PeonyRequest.class.getSimpleName();

    private final Class<T> clazz;
    private final String mUrl;
    private final EndpointRequest mRequestObject;
    private final OnRequestProcessLister mProcessListener;

    private String mCacheKey;
    private long mCacheDuration;
    private long mRefreshDuration;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param clazz Relevant class object, for Jackson's reflection
     */
    public PeonyRequest(String url, EndpointRequest requestObject, Class<T> clazz,
                        Response.Listener<T> listener, Response.ErrorListener volleyErrorListener,
                        OnRequestProcessLister processLister) {
        super(Request.Method.POST, url, volleyErrorListener);
        mUrl = url;
        this.clazz = clazz;
        this.listener = listener;
        this.mProcessListener = processLister;
        mRequestObject = requestObject;
        mMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //mMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
        //    @Override
        //    public Object findFilterId(Annotated a) {
        //        return null;
        //    }
        //});

        //to avoid duplicate request
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                2 * 60 * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        setRetryPolicy(mRetryPolicy);
        setShouldCache(false);
    }

    /**
     * 设置缓存key和时间
     *
     * @param mCacheKey mCacheKey
     * @param mCacheDuration mCacheDuration
     * @param mRefreshDuration mRefreshDuration
     */
    public void setCacheControl(String mCacheKey, long mCacheDuration, long mRefreshDuration) {
        this.mCacheKey = mCacheKey;
        this.mCacheDuration = mCacheDuration;
        this.mRefreshDuration = mRefreshDuration;
    }

    @Override
    public String getCacheKey() {
        return mCacheKey == null ? super.getCacheKey() : mCacheKey;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = mRequestObject.getHeaders();
        if (headers == null) {
            headers = super.getHeaders();
        }
        String signature = getSignature(mRequestObject);
        headers.put(HEADER_SECRET, signature);
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mRequestObject == null) return null;

        try {
            ObjectWriter objectWriter = mMapper.writer().withDefaultPrettyPrinter();
            String json = objectWriter.writeValueAsString(mRequestObject);
            showJSON(mRequestObject.isShowJSON(), mUrl, "Request", json);
            return json.getBytes();
        } catch (JsonProcessingException ex) {
            showJSON(mRequestObject.isShowJSON(), mUrl, "[In getBody]", ex.getMessage());
            return null;
        }
    }

    //public OnRequestProcessLister getProcessListener() {
    //    return mProcessListener;
    //}

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = getJsonString(response.data, HttpHeaderParser.parseCharset(response.headers));
            showJSON(mRequestObject.isShowJSON(), null, "Response", json);

            T result = mMapper.readValue(json, clazz);
            setJsonDebugField(json, result);
            return Response.success(result, getCacheEntry(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 获取Json字符串
     */
    private String getJsonString(byte[] data, String charsetName) {
        StringBuilder sb = new StringBuilder();
        if (data != null && data.length > 1) {
            byte[] h = new byte[2];
            h[0] = data[0];
            h[1] = data[1];
            int head = (h[0] << 8) | (h[1] & 0xFF);
            boolean t = head == 0x1f8b; // 是否压缩

            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                if (t) { // 压缩
                    inputStream = new GZIPInputStream(bis);
                } else {// 非压缩
                    inputStream = bis;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将网络响应的原始数据流设置到response中响应字段中
     *
     * @param json json
     * @param result result
     */
    private void setJsonDebugField(String json, T result) {
        if (!mRequestObject.isShowJSON()) return;
        Class clazz = result.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(JsonDebugAnnotation.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(result, json);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
    }

    public Cache.Entry getCacheEntry(NetworkResponse response) {
        Map<String, String> headers = response.headers;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.responseHeaders = headers;

        long currentTime = System.currentTimeMillis();
        entry.ttl = mCacheDuration == -1 ? Long.MAX_VALUE : currentTime + mCacheDuration;
        entry.softTtl = mRefreshDuration == -1 ? Long.MAX_VALUE : currentTime + mRefreshDuration;
        return entry;
    }

    public void setCustomRetryPolicy(RetryPolicy customRetryPolicy) {
        setRetryPolicy(customRetryPolicy);
    }
}