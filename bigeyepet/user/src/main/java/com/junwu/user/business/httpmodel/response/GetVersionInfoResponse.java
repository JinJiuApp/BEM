package com.junwu.user.business.httpmodel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.junwu.common_net.base.LFBaseResponse;
import com.junwu.user.business.model.VersionInfoModel;


/**
 * Created by Ted on 2015/5/22.
 */
public class GetVersionInfoResponse extends LFBaseResponse {

    @JsonProperty("data")
    private VersionInfoModel versionInfo;

    public VersionInfoModel getVersionInfo() {
        return versionInfo;
    }
}
