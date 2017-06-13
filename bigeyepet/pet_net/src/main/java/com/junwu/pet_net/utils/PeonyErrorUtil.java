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

package com.junwu.pet_net.utils;


import com.junwu.pet_net.base.PeonyError;

/**
 * 错误类型处理
 */
public class PeonyErrorUtil {
    public static String getVolleyErrorMessage(PeonyError error) {
        if (error == null) return "";
        switch (error.getErrorType()) {
            case PeonyError.ERROR_NO_CONNECTION:
                return "没有网络,请打开网络连接";
            case PeonyError.ERROR_NETWORK:
                return "网络连接错误";
            case PeonyError.ERROR_TIMEOUT:
                return "网络超时";
            case PeonyError.ERROR_SERVER:
                return "服务器异常,请稍后重试";
            case PeonyError.ERROR_PARSE:
                return "数据反序列化错误";
            default:
                return error.getMessage();
        }
    }
}
