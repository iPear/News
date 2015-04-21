package com.sdust.news.homepagers;
/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-4-3 下午2:11:32
 * 
 * 描 述：
 * 		Setting
 * 修订历史：
 * 
 * ===================================================================
 */
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sdust.news.base.BasePager;

public class SettingPager extends BasePager {

	public SettingPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView textView = new TextView(context);
		textView.setText("Setting");
		return textView;
	}

	@Override
	public void initData() {
	}

}
