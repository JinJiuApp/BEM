package com.jaryjun.common_base.ops;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Ted on 2015/5/25.
 */
public class LFIoOps {
    public static final String SD_ROOT_FOLDER_NAME = "WuKong";

    /**
     * 得到当前存储设备的目录
     */
    public static final String SD_ROOT_PATH = Environment.getExternalStorageDirectory()
            + File.separator + SD_ROOT_FOLDER_NAME + File.separator;
    /**
     * 获取扩展SD卡设备状态
     */
    private static String SDStateString = Environment.getExternalStorageState();


    public static void initBaseAppFolder() {
        if (isHaveStorage()) {
            File baseFolder = new File(Environment.getExternalStorageDirectory() + File.separator + SD_ROOT_FOLDER_NAME);
            if (!baseFolder.isDirectory() || !baseFolder.exists()) {
                baseFolder.mkdir();
            }
        } else {

            System.err.println("初始化资源文件夹失败");
        }
    }

    public static void writeFile(String fileNme, String fileContent) {
        if (isHaveStorage()) {
            writeFile(SD_ROOT_PATH, fileNme, fileContent);
        }
    }

    private static void writeFile(String filePath, String fileNme, String mContent) {
        if (TextUtils.isEmpty(mContent)) {
            return;
        }
        File folder = new File(filePath);
        if (folder != null && !folder.exists()) {
            if (!folder.mkdir() && !folder.isDirectory()) {
                System.err.println("创建文件失败");
                return;
            }
        }
        String targetPath = filePath + fileNme;
        File targetFile = new File(targetPath);
        if (targetFile != null) {
            FileWriter ss;
            try {
                ss = new FileWriter(targetFile, false);
                ss.append(mContent + "\r\n");
                ss.flush();
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(String filePath, String fileName) {
        String result = "";
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                return result;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            bufReader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isHaveStorage() {
        if (!SDStateString.equals(Environment.MEDIA_MOUNTED)) {
            System.err.println("SD card is not available/write able right now.");

            return false;
        }
        return true;
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }
}
