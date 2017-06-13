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

package com.jaryjun.common_base.utils;

import android.text.TextUtils;

import java.util.Random;

/**
 * Created by Ted on 2015/10/14.
 */
public class StringUtil {
    /**
     * 对比两个版本号是否相当 版本号必须以.作为分隔
     *
     * @param source
     * @param compare
     * @return
     */
    public static boolean isBigThan(String source, String compare) {
        boolean result = false;
        boolean hasCompare = false;
        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(compare)) return result;
        String[] sourceS = source.split("[.]");
        String[] compareS = compare.split("[.]");
        int sCount = sourceS.length;
        int cCount = compareS.length;
        int minCount = Math.min(sCount, cCount);
        if (minCount < 1) return result;

        for (int i = 0; i < minCount; i++) {
            String sStr = sourceS[i];
            String cStr = compareS[i];
            if (TextUtils.isEmpty(sStr)) sStr = "0";
            if (TextUtils.isEmpty(cStr)) cStr = "0";
            int s = Integer.parseInt(sStr);
            int c = Integer.parseInt(cStr);
            if (s != c) {
                result = s > c;
                hasCompare = true;
                break;
            }
        }
        if (!hasCompare) {
            result = sCount > cCount;
        }
        return result;
    }

  /***
   * 生成指定长度的随机字符串
   * @param length 长度
   * @return 结果
   */
    public static String getRandomString(int length) {
        //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
