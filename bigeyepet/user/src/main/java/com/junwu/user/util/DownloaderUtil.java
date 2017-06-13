package com.junwu.user.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.jaryjun.common_base.component.event.MyEvent;
import com.jaryjun.common_base.ops.LFIoOps;
import com.jaryjun.common_base.utils.ToastUtil;
import com.junwu.user.R;

import java.io.File;
import java.util.List;

import de.greenrobot.event.EventBus;

/***
 * 下载工具类
 */
public class DownloaderUtil {
    private static DownloaderUtil mInstance;
    private Context mContext;
    private String mUrl;
    private DownloadManager mDownloadManager;
    private long mDownloadReference;
    private DownloadObserver mDownloadObserver;

    private DownloaderUtil() {
    }

    public static DownloaderUtil getInstance() {
        synchronized (DownloaderUtil.class) {
            if (mInstance == null) {
                mInstance = new DownloaderUtil();
            }
        }
        return mInstance;
    }

    /**
     * 检测到新版本之后，调用该方法进行下载
     *
     * @param context
     * @param url
     */
    public void startDownloadApk(Context context, String url, String version) {
        if (TextUtils.isEmpty(url)) {
            System.out.println("Url can not be null");

            return;
        }
        if (queryDownloadStatus() == DownloadStatus.STATUS_RUNNING) {
            System.out.println("downloader is running");

            return;
        }
        mContext = context;
        mUrl = url.trim();
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(mUrl);
        Uri downloadFile = getDownloadFilePath(context, version);
        if (uri == null || downloadFile == null) {
            System.out.println("file control error");

            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationUri(downloadFile);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(context.getString(R.string.app_name));
        mDownloadReference = mDownloadManager.enqueue(request);
    }

    public long getDownloadReference() {
        return mDownloadReference;
    }

    public void setDownloadReference(long reference) {
        mDownloadReference = reference;
    }

    private Uri getDownloadFilePath(Context context, String version) {
        Uri uri;
        if (TextUtils.isEmpty(version)) version = System.currentTimeMillis() + "";
        String folderPath;
        if (LFIoOps.isHaveStorage()) {
            folderPath = LFIoOps.SD_ROOT_PATH + "download";
        } else {
            if (null != context.getExternalCacheDir()) {
                folderPath = context.getExternalCacheDir().getPath() + File.separator + "download";
            } else {
                return null;
            }
        }
        File downFolder = new File(folderPath);
        if(!downFolder.isDirectory()){
            downFolder.mkdir();
        }
        String downFilePath = folderPath+ File.separator + version + ".apk";
        System.out.println("file path:"+downFilePath);

        File file = new File(downFilePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
        uri = Uri.fromFile(file);
        return uri;
    }

    private enum DownloadStatus {
        STATUS_RUNNING,
        STATUS_COMPLETED,
    }

    private DownloadStatus queryDownloadStatus() {
        if (mDownloadReference > 0) {
            return DownloadStatus.STATUS_RUNNING;
        } else {
            return DownloadStatus.STATUS_COMPLETED;
        }

    }

    public static class DownloaderUtilReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                System.out.println("downloader util receiver intent || action is null");

                return;
            }
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                DownloadManager.Query query = new DownloadManager.Query();
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                if (id == DownloaderUtil.getInstance().getDownloadReference()) {
                    DownloaderUtil.getInstance().setDownloadReference(-1);
                    query.setFilterById(id);
                    Cursor cursor = manager.query(query);
                    String filename = "";
                    if(cursor.moveToFirst()) {
                        filename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    }
                    if(TextUtils.isEmpty(filename)){
                        ToastUtil.show(context,"文件下载失败，请重试");
                        return;
                    }
                    File apkFile = new File(filename);
                    if(!apkFile.exists()){
                        ToastUtil.show(context,"文件下载失败，请重试");
                        return;
                    }
                    //Uri uri = dm.getUriForDownloadedFile(reference);
                    Uri uri = Uri.fromFile(apkFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    if(null == uri){

                        System.out.println("uri is null");
                        ToastUtil.show(context,"文件下载失败，请重试");
                        return;
                    }
                    if(!isApkInstallAvailable(installIntent,context)){
                        System.out.println("Can not find Activity for application/vnd.android.package-archive");

                        ToastUtil.show(context,"调用系统安装器失败，请重试");
                        return;
                    }
                    try {
                        context.startActivity(installIntent);
                    }catch (Exception ex){
                        System.out.println("Can not find Activity for application/vnd.android.package-archive");

                    }
                }
            }

        }
    }

    /***
     * 检测系统安装器是否能调用
     * @param intent
     * @return
     */
    private static boolean isApkInstallAvailable(Intent intent, Context context) {
        if(null == intent)return false;
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public DownloadObserver getDownloadObserver(Handler handler, long downId, Context context){
        mDownloadObserver = new DownloadObserver(handler,downId,context);
        return mDownloadObserver;
    }

    public DownloadObserver getDownloadObserver(){
        return  mDownloadObserver;
    }

    public static class DownloadObserver extends ContentObserver {
        private Handler mHandler;
        private long downId;
        private Context mContext;

        public DownloadObserver(Handler handler, long downId, Context context) {
            super(handler);
            mHandler = handler;
            this.downId = downId;
            mContext = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            DownloadManager.Query query = new DownloadManager.Query().setFilterById(downId);
            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            //这个就是数据库查询啦
            Cursor cursor = downloadManager.query(query);
            while (cursor.moveToNext()) {
                int mDownload_so_far = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int mDownload_all = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                int mProgress = (int)(((float)mDownload_so_far  / mDownload_all)*100);
                if (mHandler != null) {
                mHandler.sendMessage(mHandler.obtainMessage(101,mProgress));
                }
                MyEvent.CommonEvent evt = MyEvent.CommonEvent.Event_DownLoad_progress;
                evt.setObject(mProgress);
                EventBus.getDefault().post(evt);
            }
        }
    }

}
