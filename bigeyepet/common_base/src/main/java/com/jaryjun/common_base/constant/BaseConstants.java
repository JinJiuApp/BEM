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

package com.jaryjun.common_base.constant;

/**
 * Created by youj on 2015/8/18.
 * BaseConstants
 */
public interface BaseConstants {

    /**
     * 业务模式 -- 二手房
     */
    int APP_BIZ_OWN_HOUSE = 0;

    /**
     * 业务模式 -- 新房
     */
    int APP_BIZ_NEW_HOUSE = 1;

    /**
     * 服务类型 -- 房东
     */
    int APP_BIZ_LANDLORD = 2;

    /**
     * 服务类型 -- 金融支付
     */
    int APP_BIZ_FINANCE = 3;

    /**
     * 业务模式 -- 租房
     */
    int APP_BIZ_RENT_HOUSE = 4;

    /**
     * 城市类型 国内
     */
    int CITY_CHINA = 1;
    /**
     * 城市类型 国外
     */
    int CITY_FOREIGN = 2;

    /**
     * 悟空找房安卓app，在悟空找房所有app体系中的编号
     */
    String LFAPP = "015";
}
