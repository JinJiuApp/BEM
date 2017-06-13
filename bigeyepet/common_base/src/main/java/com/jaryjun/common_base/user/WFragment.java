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

package com.jaryjun.common_base.user;

import android.support.v4.app.Fragment;

/**
 * Created by youj on 2015/4/13.
 * Fragment 基类
 */
public class WFragment extends Fragment {

    @Override
    public void onPause() {
        super.onPause();
        //UMengStatManager.getIns().onPageEnd(this.getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
       // UMengStatManager.getIns().onPageStart(this.getClass().getName());
    }

}