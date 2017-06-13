/*
 *******************************************
 * File: XAdapter.java
 ********************************************/
package com.jaryjun.common_base.widget.pullrefresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: XAdapter
 * @Description: TODO(描述: 万能适配器)
 */
public abstract class AdapterBase<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDataList;
	protected ArrayList<String> DataList;
	protected int mLayoutId;
	protected IAdpBaseListener mListener;

	public void setAdpListener(IAdpBaseListener mListener) {
		this.mListener = mListener;
	}

	public AdapterBase() {
	}

	public AdapterBase(Context mContext, List<T> mDataList, int mLayoutId) {
		this.mContext = mContext;
		this.mDataList = mDataList;
		this.mLayoutId = mLayoutId;
		mInflater = LayoutInflater.from(mContext);

	}

	/**
	 *
	 * @param
	 */
	public void setDataList(List<T> dataList) {
		this.mDataList = dataList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null == mDataList)
			return 0;
		return mDataList.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (null == convertView)
			convertView = mInflater.inflate(mLayoutId, null);
		Convert(position, convertView);
		return convertView;
	}

	public abstract void Convert(int position, View convertView);

	protected SparseArray<View> viewHolder;

	/**
	 *
	 * @param view
	 * @param id
	 * @return
	 */
	protected <T extends View> T Get(View view, int id) {
		viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	/**
	 * @param
	 * @param
	 * @param text
	 */
	protected void SetText(View v, String text) {
		if (null == v)
			return;
		if (TextUtils.isEmpty(text))
			text = "";
		if (v instanceof TextView) {// TextView
			((TextView) v).setText(text);
			return;
		}
		if (v instanceof Button) {// Button
			((Button) v).setText(text);
			return;
		}
		if (v instanceof CheckBox) {// CheckBox
			((CheckBox) v).setText(text);
			return;
		}
		if (v instanceof RadioButton) {// RadioButton
			((RadioButton) v).setText(text);
			return;
		}
		if (v instanceof EditText) {// EditText
			((EditText) v).setText(text);
			return;
		}
	}
	/**Item点击*/
	public static final int CLICK_ITEM=0x101;
	public interface IAdpBaseListener {
		/**
		 * Item事件
		 * @param data
		 */
		public void onLazyAdpListener(int key, int position, Object data);
	}
}
