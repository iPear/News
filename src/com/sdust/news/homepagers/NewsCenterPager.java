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
 * 创建日期：2015-4-3 下午2:12:59
 * 
 * 描 述：
 * 		NewsCenter
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdust.news.MainActivity;
import com.sdust.news.R;
import com.sdust.news.base.BasePager;
import com.sdust.news.bean.NewsCenter;
import com.sdust.news.bean.NewsCenter.NewsData;
import com.sdust.news.newspagers.NewsMainPager;
import com.sdust.news.newspagers.NewsOutPager;
import com.sdust.news.newspagers.NewsSchoolPager;
import com.sdust.news.utils.GsonUtils;
import com.sdust.news.utils.SPUtil;
import com.sdust.news.utils.SdustApi;

public class NewsCenterPager extends BasePager {
	@ViewInject(R.id.news_center_fl)
	private FrameLayout news_center_fl;
	@ViewInject(R.id.layout_loading)
	private FrameLayout layout_loading;

	public NewsCenterPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.frame_news_center, null);
		ViewUtils.inject(this, view);
		initTitleBar(view);
		return view;
	}

	@Override
	public void initData() {
		System.out.println("news");
		String result = SPUtil.getString(context,
				SdustApi.NEWS_CENTER_CATEGORIES, "");
		if (!TextUtils.isEmpty(result)) {
			processData(result);
		}
		getNewsCenterData();
	}

	/**
	 * 数据缓存: 1 数据库 2 文件缓存 3 sp 4 sd卡
	 * 
	 * 
	 */
	private void getNewsCenterData() {
		loadData(HttpMethod.GET, SdustApi.NEWS_CENTER_CATEGORIES, null,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						SPUtil.saveString(context,
								SdustApi.NEWS_CENTER_CATEGORIES,
								responseInfo.result);
						layout_loading.setVisibility(View.GONE);
						processData(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	private List<String> menuLists = new ArrayList<String>();
	private List<BasePager> pagers = new ArrayList<BasePager>();

	protected void processData(String result) {

		NewsCenter newCenter = GsonUtils.json2Bean(result, NewsCenter.class);

		menuLists.clear();

		for (NewsData newsData : newCenter.data) {
			menuLists.add(newsData.title);
		}

		((MainActivity) context).getMenuFragment().initMenu(menuLists);

		pagers.add(new NewsMainPager(context, newCenter.data.get(0)));
		pagers.add(new NewsSchoolPager(context));
		pagers.add(new NewsOutPager(context));

		switchFragment(0);

	}

	/**
	 * Switch Fragment
	 * 
	 * @param postion
	 */
	public void switchFragment(int postion) {
		BasePager basePager = pagers.get(postion);
		switch (postion) {
		case 0:
			news_center_fl.removeAllViews();
			news_center_fl.addView(pagers.get(postion).getRootView());
			txt_title.setText("要闻");
			break;

		case 1:
			news_center_fl.removeAllViews();
			news_center_fl.addView(pagers.get(postion).getRootView());
			txt_title.setText("校内");
			break;
		case 2:
			news_center_fl.removeAllViews();
			news_center_fl.addView(pagers.get(postion).getRootView());
			txt_title.setText("周边");
			break;
		}
		basePager.initData();
	}

}
