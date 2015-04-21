package com.sdust.news.newspagers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sdust.news.base.BasePager;

public class NewsOutPager extends BasePager {

	public NewsOutPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView textView = new TextView(context);
		textView.setText("图片");
		return textView;
	}

	@Override
	public void initData() {
	}

}
