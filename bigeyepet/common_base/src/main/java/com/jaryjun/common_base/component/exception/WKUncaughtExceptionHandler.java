package com.jaryjun.common_base.component.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.config.LFAppConfig;
import com.jaryjun.common_base.ops.LFAppConfigOps;
import com.jaryjun.common_base.user.LFApplication;
import com.jaryjun.common_base.ops.LFIoOps;
import com.jaryjun.common_base.utils.DateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

/**
 * Created by Ted on 14-7-3.
 * WKUncaughtExceptionHandler
 */
public class WKUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private Context mContext;
    private PendingIntent mIntent;
    private UncaughtExceptionHandler defaultExceptionHandler;
    private static WKUncaughtExceptionHandler WKUncaughtExceptionHandler;

    public static synchronized WKUncaughtExceptionHandler getInstance() {
        if (WKUncaughtExceptionHandler == null) {
            WKUncaughtExceptionHandler = new WKUncaughtExceptionHandler();
        }
        return WKUncaughtExceptionHandler;
    }

    private WKUncaughtExceptionHandler() {

    }

    public void init(Context context) {
        if (LFAppConfigOps.isDev()) return;
        mContext = context;
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (null != defaultExceptionHandler) {
            //UMengStatManager.getIns().reportError(ex);
            handleUncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private void setAppDie() {
        if (mContext instanceof LFApplication) {
            ((LFApplication) mContext).getPref().commitBoolean(R.string.is_app_active, false);
        }
    }

    /**
     * 处理错误信息
     *
     * @param ex ex
     */
    protected void handleUncaughtException(Thread thread, Throwable ex) {
        setAppDie();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        ex.printStackTrace();
        collectExInfo(thread, ex);
        /**重启应用*/
        reboot();
    }

    public void setIntent(PendingIntent mIntent) {
        this.mIntent = mIntent;
    }

    private void collectExInfo(Thread thread, Throwable ex) {
        String exInfo = getPlatformInfo() +
                "\r\n" + getVersionInfo() +
                "\r\n" + getMobileInfo() +
                "\r\n" + getErrorInfo(ex) +
                "\r\n" + "threadName:" +
                (null == thread ? "unKnown" : thread.getName());

        /**错误信息写到本地*/
        writeErrorInfoToFile(exInfo);
    }

    private void reboot() {
        if (mIntent != null && null != mContext) {
            AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mIntent);
            System.exit(0);
            return;
        }
       // UMengStatManager.getIns().killProcess();
        System.exit(1);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private void writeErrorInfoToFile(String exceptionInfo) {
        if (TextUtils.isEmpty(exceptionInfo)) return;
        String folderName = LFIoOps.SD_ROOT_PATH;
        String timeLabel = DateUtil.getCalendarStrBySimpleDateFormat(System.currentTimeMillis(), DateUtil.SIMPLEFORMATTYPESTRING2);
        long timeStamp = System.currentTimeMillis();
        String fileName = "crash-" + timeLabel + "-" + timeStamp + ".log";
        String targetPath = folderName + fileName;
        File targetFile = new File(targetPath);
        FileWriter fileWriter;
        try {
            /**记录最近一次崩溃日志*/
            fileWriter = new FileWriter(targetFile, false);
            fileWriter.append(exceptionInfo).append("\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取错误的信息
     *
     * @param arg1 arg1
     * @return String
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        String[] errorInfo = writer.toString().split("\n\tat");
        String error = "";
        int length = errorInfo.length > 6 ? 6 : errorInfo.length;
        for (int i = 0; i < length; i++) {
            error = error + errorInfo[i] + "\n\t";
        }
        pw.close();
        return "异常内容：" + error;
    }

    /**
     * 获取手机的硬件信息
     *
     * @return String
     */
    private String getMobileInfo() {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            sb.append("手机硬件信息：");
            int length = fields.length;
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                if (field.equals(fields[length / 2])) {
                    sb.append(name).append("=").append(value).append("||\r\n");
                } else {
                    sb.append(name).append("=").append(value).append("||");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取手机的版本信息
     *
     * @return getVersionInfo
     */
    private String getVersionInfo() {
        return "版本号信息：" + LFAppConfig.getVersionName() + "  " + LFAppConfig.getVersionCode();
    }

    private String getPlatformInfo() {
        return LFAppConfig.getChannelNo();
    }
}
