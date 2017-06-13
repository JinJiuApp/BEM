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

package com.jaryjun.common_base.utils;

import android.text.TextUtils;

import com.jaryjun.common_base.ops.LFIoOps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ted on 2016/5/11.
 *
 * @ com.wukong.base.util
 */
public class FileUtil {

  public static void writePushInfoToFile(String pushContent) {
    if (TextUtils.isEmpty(pushContent)) return;
    String folderName = LFIoOps.SD_ROOT_PATH;
    String timeLabel = DateUtil.getCalendarStrBySimpleDateFormat(System.currentTimeMillis(),
        DateUtil.SIMPLEFORMATTYPESTRING2);
    long timeStamp = System.currentTimeMillis();
    String fileName = "push-" + timeLabel + "-" + timeStamp + ".log";
    String targetPath = folderName + fileName;
    File targetFile = new File(targetPath);
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(targetFile, false);
      fileWriter.append(pushContent).append("\r\n");
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeEvokeInfoToFile(String evokeInfo) {
    if (TextUtils.isEmpty(evokeInfo)) return;
    String folderName = LFIoOps.SD_ROOT_PATH;
    String timeLabel = DateUtil.getCalendarStrBySimpleDateFormat(System.currentTimeMillis(),
        DateUtil.SIMPLEFORMATTYPESTRING2);
    long timeStamp = System.currentTimeMillis();
    String fileName = "evoke-" + timeLabel + "-" + timeStamp + ".log";
    String targetPath = folderName + fileName;
    File targetFile = new File(targetPath);
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(targetFile, false);
      fileWriter.append(evokeInfo).append("\r\n");
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
