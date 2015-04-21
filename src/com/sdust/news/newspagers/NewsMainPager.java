package com.sdust.news.newspagers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdust.news.R;
import com.sdust.news.base.BasePager;
import com.sdust.news.bean.NewsCenter.ChildrenItem;
import com.sdust.news.bean.NewsCenter.NewsData;
import com.sdust.news.view.pagerindicator.TabPageIndicator;

public class NewsMainPager extends BasePager {

	private NewsData newsData;
	@ViewInject(R.id.indicator)
	private TabPageIndicator indicator;
	@ViewInject(R.id.pager)
	private ViewPager view_pager;

	public NewsMainPager(Context context, NewsData newsData) {
		super(context);
		this.newsData = newsData;
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.fragment_news, null);
		ViewUtils.inject(this, view);
		return view;
	}

	private List<NewsItem> lists = new ArrayList<NewsItem>();
	private int mCurrentPosition = 0;

	@Override
	public void initData() {
		lists.clear();
		for (ChildrenItem category : newsData.children) {
			lists.add(new NewsItem(context, category.url));
		}

		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(lists);

		view_pager.setAdapter(pagerAdapter);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				NewsItem newsItem = lists.get(arg0);

				if (arg0 == 0) {
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}

				newsItem.initData();

				mCurrentPosition = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		lists.get(0).initData();
		indicator.setViewPager(view_pager);
		indicator.setCurrentItem(mCurrentPosition);
	}

	private class ViewPagerAdapter extends PagerAdapter {

		private List<NewsItem> pages;

		public ViewPagerAdapter(List<NewsItem> lists) {
			this.pages = lists;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(pages.get(position)
					.getRootView());
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return newsData.children.get(position).title;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(pages.get(position).getRootView(),
					0);
			return pages.get(position).getRootView();
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
