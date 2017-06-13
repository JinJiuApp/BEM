/*
 *******************************************
 * File: SupplyAdapter.java
 * Author: Lee
 * Date: 2016年5月4日
 ********************************************/
package com.junwu.user.business.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaryjun.common_base.widget.pullrefresh.AdapterBase;
import com.junwu.user.R;
import com.junwu.user.business.model.MainItemModel;

import java.util.List;

/**
 * 
 * @ClassName: SupplyAdapter
 * @Description: TODO(描述: 萌宠适配器)
 * @author Lee
 * @date 2016年5月4日 上午10:30:25
 * @version V1.0
 */
public class MainAdapter extends AdapterBase<MainItemModel> {
	public MainAdapter(Context ctx, List<MainItemModel> list) {
		super(ctx, list, R.layout.item_main);
	}

	@Override
	public void Convert(final int position, View convertView) {
		// TODO Auto-generated method stub
		ImageView iv_img = Get(convertView, R.id.iv_img);
		TextView tv_name = Get(convertView, R.id.tv_name);
		TextView tv_pet = Get(convertView, R.id.tv_pet);
		TextView tv_old = Get(convertView, R.id.tv_old);
		TextView tv_spec = Get(convertView, R.id.tv_spec);
		ImageView iv_pic = Get(convertView, R.id.iv_pic);
		TextView tv_evaluate_count = Get(convertView, R.id.tv_evaluate_count);
		TextView tv_collect_count = Get(convertView, R.id.tv_collect_count);
		ImageView iv_share = Get(convertView, R.id.iv_share);

		/*SetText(tv_title, getItem(position).getCategoryName());
		SetText(tv_time, getItem(position).getTimeFormat());*/


		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

}
