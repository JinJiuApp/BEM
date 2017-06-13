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
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.ops.LFUiOps;
import com.jaryjun.common_base.utils.SystemUtil;


/**
 * Created by Ted on 2015/10/15.
 * 用来适应透明状态栏的页面，默认具有顶部padding值
 */
public class LFFitStatusBarLayout extends LinearLayout {
  private Context mContext;
  private View mTopView;
  private View mChildView;
  private int mBgColor = 0;
  private int mBgResId = -1;

  public LFFitStatusBarLayout(Context context) {
    super(context);
    initBase(context);
    initFromAttributes(context, null);
  }

  public LFFitStatusBarLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    initBase(context);
    initFromAttributes(context, attrs);
  }

  public LFFitStatusBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initBase(context);
    initFromAttributes(context, attrs);
  }

  public LFFitStatusBarLayout(Context context, View childView, int bgColorId) {
    super(context);
    initBase(context);
    this.mChildView = childView;
    if (this.mChildView.getLayoutParams() == null) {
      this.mChildView.setLayoutParams(
          new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT));
    }
    mBgColor = context.getResources().getColor(bgColorId > 0 ? bgColorId : android.R.color.white);
    onFinishInflate();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (LFUiOps.isSupportTranslucentStatus(getContext())) {
      applyToNewRootLayout();
    } else {
      if (null != mChildView) this.addView(mChildView);
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  /***
   * 设置顶部栏背景颜色
   */
  public void setFitStatusBarBgColor(@ColorRes int bgColorResId) {
    if (bgColorResId < 0) return;
    mBgColor = mContext.getResources().getColor(bgColorResId);
    mTopView.setBackgroundColor(mBgColor);
    adjustStatusColor(mBgColor);
  }

  /***
   * 设置顶部栏VIEW透明度
   */
  public void setFitStatusBarAlpha(@FloatRange(from = 0, to = 1) float alpha) {
    if (alpha < 0 || alpha > 1 || mTopView == null ||
        !LFUiOps.isSupportTranslucentStatus(getContext())) {
      return;
    }
    mTopView.setAlpha(alpha);
  }

  /**
   * 设置StatusBar是否可见
   */
  public void setStatusBarVisible(@NonNull boolean flag) {
    if (!LFUiOps.isSupportTranslucentStatus(getContext()) || mTopView == null) return;
    if ((flag && mTopView.getVisibility() != View.VISIBLE) || (!flag
        && mTopView.getVisibility() != View.GONE)) {
      mTopView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
  }

  /*******************
   * private method
   *******************************************************/
  private void initBase(Context context) {
    mContext = context;
    this.setOrientation(VERTICAL);
    this.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
    mTopView = new View(mContext);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LFUiOps.getStatusBarHeight(mContext));
    mTopView.setLayoutParams(layoutParams);
  }

  private void initFromAttributes(Context context, AttributeSet attrs) {
    if (attrs != null) {
      TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FitStatusBarLayout);
      mBgColor = array.getColor(R.styleable.FitStatusBarLayout_status_bar_bg_color,
          context.getResources().getColor(android.R.color.white));
      mBgResId = array.getResourceId(R.styleable.FitStatusBarLayout_status_bar_bg_id, -1);
      array.recycle();
    } else {
      mBgColor = context.getResources().getColor(android.R.color.white);
      mBgResId = -1;
    }
  }

  private void applyToNewRootLayout() {
    if (null == mChildView) {
      if (getChildCount() != 1) {
        throw new IllegalArgumentException("LFFitStatusBarLayout must contain only one layout");
      }
      mChildView = this.getChildAt(0);
      this.removeViewAt(0);
    }
    if (mBgResId > 0) {
      mTopView.setBackgroundResource(mBgResId);
      adjustStatusColor(getResources().getColor(mBgResId));
    } else if (mBgColor != 0) {
      mTopView.setBackgroundColor(mBgColor);
      adjustStatusColor(mBgColor);
    }
    this.addView(mTopView);
    this.addView(mChildView);
  }

  private void adjustStatusColor(int mBgColor) {
    if (SystemUtil.isMIUI() || SystemUtil.isMX()) {
      if (mBgColor == 0xfff5f5f5 || mBgColor == 0xffffffff) {
        mTopView.setBackgroundColor(0xFFA4ADB7);
      }
    }
  }
}