package com.jaryjun.common_base.ops;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.jaryjun.common_base.component.imageloader.LFImageLoaderConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * Created by youj on 2015/5/12.
 */
public class LFImageLoaderOps {

    public static void displayPic(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    public static void displayPic(String url, ImageView imageView, DisplayImageOptions options) {
        if (null == imageView) {
            return;
        }
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }


    public static void displayPic(String url, ImageView imageView, DisplayImageOptions options, ImageLoadingListener loadingListener) {
        if (null == imageView) {
            return;
        }
        ImageLoader.getInstance().displayImage(url, imageView, options, loadingListener);
    }

    public static void onPause() {
        ImageLoader.getInstance().pause();
    }

    public static void onResume() {
        ImageLoader.getInstance().resume();
    }

    /**
     * 读取缓存图片，如果没有开启后台下载
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static BitmapDrawable getCacheImage(String url, int width, int height) {
        if (TextUtils.isEmpty(url)) return null;
        if (ImageLoader.getInstance().getDiskCache() != null) {
            File file = ImageLoader.getInstance().getDiskCache().get(url);
            if (file != null) {
                String path = file.getPath();
                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);

                int be = 1;//be=1表示不缩放
                if (width > 0 && options.outWidth > options.outHeight && options.outWidth > width) {//如果宽度大的话根据宽度固定大小缩放
                    be = options.outWidth / width;
                } else if (height > 0 && options.outHeight > options.outWidth && options.outHeight > height) {//如果高度高的话根据宽度固定大小缩放
                    be = options.outHeight / height;
                }
                options.inSampleSize = be <= 0 ? 1 : be;//设置缩放比例
                options.inJustDecodeBounds = false;
                if (options.outHeight == 0 || options.outWidth == 0) {
                    return null;
                } else {
                    return new BitmapDrawable(BitmapFactory.decodeFile(path, options));
                }
            }
        }
        ImageLoader.getInstance().loadImage(url, null);
        return null;
    }


    public static void showMapSnapShoot(double latitude, double longitude, ImageView imageView, boolean hasMarker, boolean isForeign) {
        StringBuilder builder = new StringBuilder();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (isForeign) {
            builder.append("http://maps.google.cn/maps/api/staticmap?")
                    .append("center=" + latitude + "," + longitude + "&")
                    .append("zoom=" + 17 + "&")
                    .append("size=" + imageView.getLayoutParams().width + "x" + imageView.getLayoutParams().height + "&scale=2&&format=png&maptype=roadmap&")
                    .append(hasMarker ? "markers=size:mid|color:red|label:S|" + latitude + "," + longitude + "&" : "")
                    .append("sensor=false");
        } else {
            builder.append("http://restapi.amap.com/v3/staticmap?")
                    .append("location=" + longitude + "," + latitude + "&")
                    .append("zoom=" + 17 + "&")
                    .append("size=" + imageView.getLayoutParams().width + "*" + imageView.getLayoutParams().height + "&")
                    .append(hasMarker ? "markers=mid,0xffa500,:" + longitude + "," + latitude + "&" : "")
                    .append("key=5345e386d81460f686640dba61b7ef03");
        }
        displayPic(builder.toString(), imageView, LFImageLoaderConfig.options_detail);
    }

}
