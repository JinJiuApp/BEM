package com.jaryjun.common_base.component.imageloader;

import android.graphics.Bitmap;

import com.jaryjun.common_base.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


/**
 * Created by Ted on 14-7-2.
 */
public final class LFImageLoaderConfig {



    /**
     * 详情页使用
     */
    public static final DisplayImageOptions options_detail = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.img_detail_loading)
            .showImageForEmptyUri(R.drawable.img_detail_empty)
            .showImageOnFail(R.drawable.img_detail_failed)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new FadeInBitmapDisplayer(1000)).build();



}
