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
 * 创建日期：2015-4-2 下午6:07:42
 * 
 * 描 述：
 * 		BasePager
 * 修订历史：
 * 
 * ===================================================================
 */
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sdust.news.MainActivity;
import com.sdust.news.R;

public abstract class BasePager {

	public Context context;
	private View view;
	public TextView txt_title;
	public SlidingMenu slidingMenu;

	public BasePager(Context context) {
		this.context = context;
		view = initView();
		slidingMenu = ((MainActivity) context).getSlidingMenu();
	}

	public View getRootView() {
		return view;
	}

	public void initTitleBar(View view) {
		ImageButton imgbtn_left = (ImageButton) view
				.findViewById(R.id.imgbtn_left);
		imgbtn_left.setImageResource(R.drawable.icon_menu);

		imgbtn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.toggle();
			}
		});
		ImageButton imgbtn_right = (ImageButton) view
				.findViewById(R.id.imgbtn_right);
		imgbtn_right.setVisibility(View.GONE);

		txt_title = (TextView) view.findViewById(R.id.txt_title);
	}

	public void loadData(HttpMethod method, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		HttpUtils httpUtils = new HttpUtils();
		if (params == null) {
			params = new RequestParams();
		}
		httpUtils.send(method, url, params, callBack);
	}

	public abstract View initView();

	public abstract void initData();
}
