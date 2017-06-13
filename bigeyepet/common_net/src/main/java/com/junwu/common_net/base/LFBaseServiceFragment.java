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

import android.os.Bundle;
import android.view.animation.Animation;

import com.jaryjun.common_base.user.LFBaseFragment;
import com.junwu.common_net.server.LFServiceTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 所有页面有服务的fragment的基类, 无服务的请直接extends {LFBaseFragment}
 * Created by youj on 2015/4/13.
 */
public abstract class LFBaseServiceFragment extends LFBaseFragment {

    private Set<String> tokens = new HashSet<>();

    private Map<String, String> similarServices = new HashMap<>();

   private List<LFServiceTask> mTasks;

    /**
     * 是否等待进场动画完成, 默认为false
     */
    protected boolean isWaitAnimationEnd = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        Animation animation = null;

 /*       if (enter && nextAnim == R.anim.common_anim_fragment_in) {
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            if (null != animation) {
                isWaitAnimationEnd = true;
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        initLoadData();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
*/
        return animation;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isWaitAnimationEnd) {
            initLoadData();
        }
    }

    /**
     * 初始化Presenter
     */
    protected void initPresenter() {
    }

    /**
     * 页面初始化完成之后load server data
     */
    protected void initLoadData() {

    }

    public void addToken(String token) {
        tokens.add(token);
    }

    /**
     * 取消上一次相同的服务（若存在）
     *
     * @param tag   tag
     * @param token token
     */
  /*  protected void cancelLastService(String tag, String token) {

        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (similarServices.containsKey(tag)) {
            String lastToken = similarServices.get(tag);
            if (null != lastToken) {
                RequestManager.getInstance(LFApplication.getInstance()).getRequestQueue().cancelAll(lastToken);
            }
            tokens.remove(lastToken);
        }
        similarServices.put(tag, token);
    }

    *//**
     * 取消当前页面的所有服务(非首页TAB Fragment，一般情况下，无需调用)
     *//*
    @SuppressWarnings("unused")
    protected void cancelAllServices() {
        for (String token : tokens) {
            if (null != token)
                RequestManager.getInstance(LFApplication.getInstance()).getRequestQueue().cancelAll(token);
        }
    }

    @Override
    public void onDestroy() {
        unSubscribe();

        for (String token : tokens) {
            if (null != token)
                RequestManager.getInstance(LFApplication.getInstance()).getRequestQueue().cancelAll(token);
        }
        super.onDestroy();
    }

    private void unSubscribe() {
        if (mTasks != null) {
            for (LFServiceTask task : mTasks) {
                task.destroy();
            }
        }
    }
*/
    public void addTask(LFServiceTask task) {
        if (mTasks == null) {
            mTasks = new ArrayList<LFServiceTask>();
        }
        mTasks.add(task);
    }

}
