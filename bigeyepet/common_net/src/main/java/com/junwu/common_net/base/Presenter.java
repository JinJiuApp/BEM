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


import com.jaryjun.common_base.user.LFApplication;
import com.junwu.common_net.server.LFServiceTask;
import com.junwu.pet_net.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youj on 2015/5/5.
 * 抽象Presenter
 */
public abstract class Presenter {

  protected String token;

  private List<LFServiceTask> mTasks;

  public void destroy() {
    if (mTasks != null) {
      for (LFServiceTask task : mTasks) {
        task.destroy();
      }
    }
    RequestManager.getInstance(LFApplication.getInstance()).getRequestQueue().cancelAll(token);
  }

  public void addTask(LFServiceTask task) {
    if (mTasks == null) {
      mTasks = new ArrayList<>();
    }
    mTasks.add(task);
  }
}
