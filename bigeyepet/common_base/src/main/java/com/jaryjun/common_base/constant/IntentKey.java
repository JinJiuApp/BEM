package com.jaryjun.common_base.constant;

/**
 * Description:activity跳转传值的key
 * Creator: ZhangJinWei
 * Date:15/10/19 下午4:22
 */
public interface IntentKey {
    /**
     * 跳转到设置密码界面的key
     */
    String INTENT_PAY_PWS_FLAG = "intent_pay_pws_flag";

    /**
     * 跳转到设置密码界面的验证码
     */
    String INTENT_PAY_PWS_VERIFY_CODE = "intent_pay_pws_verify_code";

    /**
     * 跳转到接受验证码的界面，传手机号
     */
    String INTENT_PHONE_NUM = "intent_phone_num";

    /**
     * 接收经纪人ID
     */
    String KEY_AGENT_ID = "com.wukong.ua.KEY_AGENT_ID";

    /**
     * 经纪人类型
     */
    String KEY_AGENT_TYPE = "com.wukong.ua.KEY_AGENT_TYPE";

    /**
     * 地图页打开列表，经纬度对角model
     */
    String KEY_HOUSE_LIST_LAT_LON_DIAGONAL = "com.wukong.ua.KEY_HOUSE_LIST_LAT_LON_DIAGONAL";

    /**
     * 地图页打开租房列表
     */
    String KEY_RENT_LIST = "com.wukong.ua.KEY_RENT_LIST";

    /**
     * 新房列表 城市ID
     */
    String KEY_NEW_HOUSE_LIST_CITY_ID = "com.wukong.ua.KEY_NEW_HOUSE_LIST_CITY_ID";

    /**
     * 去二手房列表，带小区ID过来
     */
    String KEY_GOTO_OWNED_HOUSE_LIST_WITH_HOUSEID = "KEY_GOTO_OWNED_HOUSE_LIST_WITH_HOUSEID";

    /**
     * 微聊进收藏记录
     */
    String KEY_ENTER_COLLECT_RECORD_FROM_IM = "com.wukong.ua.KEY_ENTER_COLLECT_RECORD_FROM_IM";

    /**
     * 收藏记录页返回房源数据给Im
     */
    String KEY_COLLECT_RECORD_HOUSE_MODEL = "com.wukong.ua.KEY_COLLECT_RECORD_HOUSE_MODEL";

    /**
     * 去系统消息列表和群邀请列表，key
     */
    String KEY_SYSTEM_MSG_LIST_OR_CHAT_GROUP_INVITATION = "KEY_SYSTEM_MSG_LIST_OR_CHAT_GROUP_INVITATION";

    /**
     * 群聊中，群的名字
     */
    String KEY_CHAT_GROUP_NAME = "KEY_CHAT_GROUP_NAME";

    /**
     * 系统消息列表中，去系统消息详情，的实体key
     */
    String KEY_SYSTEM_MSG_LIST_2_DETAIL = "KEY_SYSTEM_MSG_LIST_2_DETAIL";

    /**
     * 去群聊邀请详情页，带过来的实体key
     */
    String KEY_CHAT_GROUP_INVITATION = "KEY_CHAT_GROUP_INVITATION";
    /**
     * 传递群聊天id
     */
    String KEY_CHAT_GROUP_ID = "KEY_CHAT_GROUP_ID";

    /**
     * 去信息界面，刷新所有数据
     */
    String KEY_CONVERSATIONS_REFRESH_ALL = "KEY_CONVERSATIONS_REFRESH_ALL";

    /**
     * 去消息列表界面，来自直营租房
     */
    String KEY_CONVERSATIONS_FROM_OWN_RENT = "KEY_CONVERSATIONS_FROM_OWN_RENT";

    /**
     * 去设置头像界面，到底是和经纪人聊天，还是群聊,true 为群聊
     */
    String KEY_CHAT_AVATAR_SETTING_IS_GROUP = "KEY_CHAT_AVATAR_SETTING_IS_GROUP";

    /**
     * 新房列表和二手房列表，过滤条件弹框，来源。默认是新房列表。
     */
    String KEY_HOUSE_LIST_FILTER_FROM_NEW_HOUSE = "KEY_HOUSE_LIST_FILTER_FROM_NEW_HOUSE";

    /**
     * 去聊天界面带的参数，ImAgentModel 对应的key
     */
    String KEY_TO_CHAT_PARAMS = "KEY_TO_CHAT_PARAMS";

    /**
     * 从指定经纪人页进入经纪人详情或更换经纪人页
     */
    String KEY_ENTER_FROM_APPOINT_AGENT = "KEY_ENTER_FROM_APPOINT_AGENT";

    /**
     * 排序页面当前选中项
     */
    String KEY_HOUSE_FILTER_INDEX = "KEY_HOUSE_FILTER_INDEX";

    /**
     * 是否从首页专区进入列表
     */
    String KEY_ENTER_LIST_FROM_FIRST_PAGE = "KEY_ENTER_LIST_FROM_FIRST_PAGE";
}
