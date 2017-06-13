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

package com.junwu.user.business.evoker;

/**
 * Created by Ted on 2016/4/28.
 *
 * @ com.wukong.user.business.evoker
 */
public class ExternalEvoker {
  public static final String TARGET_TYPE_DIRECT_JUMP = "0";//直接跳转目标页面
  public static final String TARGET_TYPE_SYS_MSG = "1";//代表跳转到 系统信息 中转页【需验证身份】
  public static final String TARGET_TYPE_GROUP_INVITE = "2";//代表跳转到 群组信息 中转页面【需验证身份】

  public static final int BIZ_TYPE_OWN_HOUSE_DETAIL = 1;//二手房源详情信息
  public static final int BIZ_TYPE_AGENT_DETAIL = 2;//经纪人信息【需验证身份】
  public static final int BIZ_TYPE_PLOT_DETAIL_PAGE = 8;//小区详情评论页
  public static final int BIZ_TYPE_OWN_PLOT_DETAIL = 10;//二手房小区详情页
  public static final int BIZ_TYPE_OWN_MAP_PAGE = 11;//二手房地图页面
  public static final int BIZ_TYPE_NEW_MAP_PAGE = 12;//新房地图页面
  public static final int BIZ_TYPE_OWN_SEARCH_PAGE = 13;//二手房搜索页面
  public static final int BIZ_TYPE_NEW_SEARCH_PAGE = 14;//新房搜索页面
  public static final int BIZ_TYPE_NEW_HOUSE_DETAIL = 15;//新房详情页面
  public static final int BIZ_TYPE_LANDLORD_PAGE = 16;//卖房页面
  public static final int BIZ_TYPE_OWN_RECORD_PAGE = 17;//二手房记录页面【需登录】
  public static final int BIZ_TYPE_NEW_RECORD_PAGE = 18;//新房记录页面【需登录】
  public static final int BIZ_TYPE_OWN_COLLECT_PAGE = 19;//二手房收藏页【需登录】
  public static final int BIZ_TYPE_NEW_COLLECT_PAGE = 20;//新房收藏页【需登录】
  public static final int BIZ_TYPE_EXPLORER_PAGE = 21;//发现页一级页
  public static final int BIZ_TYPE_EXPLORER_PAGE_SUB_PAGE = 22;//发现页二级页
  public static final int BIZ_TYPE_SECRETARY_PAGE_ = 23;//悟空小秘书
  public static final int BIZ_TYPE_USER_INFO_PAGE = 24;//个人中心
  public static final int BIZ_TYPE_ACTIVITY_DETAIL = 25;//新用户活动详情页


}
