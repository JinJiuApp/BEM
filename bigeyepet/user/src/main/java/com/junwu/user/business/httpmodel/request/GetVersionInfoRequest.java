package com.junwu.user.business.httpmodel.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junwu.common_net.base.LFBaseRequest;
import com.junwu.pet_net.annotation.RequestAnnotation;


/**
 * Created by Ted on 2015/5/22.
 */
@RequestAnnotation(path = "siteSearch/updateVersion.rest")
public class GetVersionInfoRequest extends LFBaseRequest {

    @JsonIgnore
    private String os; //android/ios

    public void setOs(String os) {
        this.os = os;
    }
}
