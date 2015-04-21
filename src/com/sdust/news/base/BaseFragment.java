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
 * 创建日期：2015-3-23 下午3:36:08
 * 
 * 描 述：
 * 		抽取出的Fragment基类
 * 
 * 修订历史：
 * 
 * ===================================================================
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sdust.news.MainActivity;

public abstract class BaseFragment extends Fragment {
	public Context context;
	public SlidingMenu slidingMenu;
    public boolean is_load = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		slidingMenu = ((MainActivity) context).getSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = initView(inflater);
		return view;
	}

	public abstract View initView(LayoutInflater inflater);

	public abstract void initData(Bundle savedInstanceState);
}
