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

package com.junwu.common_net.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaryjun.common_base.config.LFAppConfig;
import com.junwu.pet_net.base.EndpointRequest;

import java.util.HashMap;

/**
 * Created by wyylling on 2/7/15.
 * LFBaseRequest
 */
public class LFBaseRequest implements EndpointRequest {

    @JsonIgnore
    private String token;

    @JsonIgnore
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    @JsonIgnore
    public HashMap<String, String> getHeaders() {
        return LFAppConfig.getHeaders();
    }

    @Override
    @JsonIgnore
    public String getProtocol() {
        return "http";
    }

    @Override
    @JsonIgnore
    public String getHost() {
        return "";
    }

    @Override
    @JsonIgnore
    public int getPort() {
        return 80;
    }

    @Override
    @JsonIgnore
    public boolean isShowJSON() {
        return LFAppConfig.isShowJson();
    }

    @Override
    @JsonIgnore
    public String getPath() {
        return "";
    }

    @JsonIgnore
    public boolean isShowLoading() {
        return false;
    }

}
