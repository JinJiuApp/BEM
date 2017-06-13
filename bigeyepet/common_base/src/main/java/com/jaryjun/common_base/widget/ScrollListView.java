/*
 *******************************************
 * File: ScrollListView.java
 * Author: Lee
 * Date: 2016年5月31日
 ********************************************/
package com.jaryjun.common_base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @ClassName: ScrollListView
 * @Description: TODO(描述: ScrollView 中嵌套 ListView)
 * @author Lee
 * @date 2016年5月31日 下午4:21:04
 * @version V1.0
 */
public class ScrollListView extends ListView {
	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollListView(Context context) {
		super(context);
	}

	public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
