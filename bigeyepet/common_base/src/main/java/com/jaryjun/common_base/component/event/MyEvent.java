package com.jaryjun.common_base.component.event;

/**
 * Description:暂时采用的Event基类
 * Creator: ZhangJinWei
 * Date:15/10/8 下午5:23
 */
public interface MyEvent {
    void setObject(Object object);

    Object getObject();

    /**
     * 这里放，暂时没有分类的或者不好分类的事件
     */
    enum CommonEvent implements MyEvent {
        /**
         * 下载进度反馈事件
         **/
        Event_DownLoad_progress;
        private Object o;

        @Override
        public void setObject(Object o) {
            this.o = o;
        }

        @Override
        public Object getObject() {
            return o;
        }
    }

    /**
     * 暂时以业务模块新房和二手房来区分，期待更好的分类
     */
    enum NewHouseEvent implements MyEvent {
        //对应之前的分类NewHouseSearchModel
        NewHouse_SearchModel,
        OwnedHouse_SearchModel,
        // 首次设置密码，重置密码成功之后通用的回调
        SET_PWS_EVENT;
        private Object o;

        @Override
        public void setObject(Object o) {
            this.o = o;
        }

        @Override
        public Object getObject() {
            return o;
        }
    }

    /**
     * 这个暂时分类为，baseL比中所有event大类
     */
    enum BaseLibEvent implements MyEvent {
        //对应之前的分类LocationEvent
        Location_Event,
        //微信支付BaseResp
        WXPAY_EVENT,
        WX_HEAD_EVENT;
        private Object o;

        @Override
        public void setObject(Object o) {
            this.o = o;
        }

        @Override
        public Object getObject() {
            return o;
        }
    }

    /*enum CommentAgentEvent implements MyEvent {
        Comment_Succeed;

        @Override
        public void setObject(Object object) {

        }

        @Override
        public Object getObject() {
            return null;
        }
    }*/
}
