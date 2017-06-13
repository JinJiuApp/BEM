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

package com.jaryjun.common_base.component.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Ted on 2015/10/26.
 * ScrollView 实现了滚动监听接口
 */
public class LFScrollView extends ScrollView {
    private OnScrollListener mOnScrollListener;

    public LFScrollView(Context context) {
        super(context);
    }

    public LFScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LFScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldL, int oldT) {
        super.onScrollChanged(l, t, oldL, oldL);
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(this.getScrollY());
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    /***
     * 滚动监听
     */
    public interface OnScrollListener {
        void onScroll(int scrollY);
    }
}
