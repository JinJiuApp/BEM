package com.jaryjun.common_base.component.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.user.LFApplication;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * 自定义图片的显示方式
 * Created by youj on 2015/5/12.
 */
public class TransitionBitmapDisPlayer implements BitmapDisplayer {

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        Drawable startDrawable = LFApplication.getInstance().getResources().getDrawable(R.drawable.bg_transparent);
        Drawable[] drawables = new Drawable[]{startDrawable, new BitmapDrawable(
            LFApplication.getInstance().getResources(), bitmap)};
        TransitionDrawable td = new TransitionDrawable(drawables);
        td.setCrossFadeEnabled(true);
        td.startTransition(300);
        imageAware.setImageDrawable(td);
    }
}
