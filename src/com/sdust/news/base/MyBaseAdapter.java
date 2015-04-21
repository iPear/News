package com.sdust.news.base;
/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-3-24 下午5:56:35
 * 
 * 描 述：
 * 
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	
	public List<T> lists;

	public View Q;
	
	public MyBaseAdapter(Context context, List<T> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	public MyBaseAdapter() {
		super();
	}

	public MyBaseAdapter(Context context, List<T> lists, View q) {
		super();
		this.context = context;
		this.lists = lists;
		Q = q;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
}
