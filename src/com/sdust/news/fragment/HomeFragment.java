package com.sdust.news.fragment;

/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-3-26 下午1:42:52
 * 
 * 描 述：
 * 		HomeFragment
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdust.news.R;
import com.sdust.news.base.BaseFragment;
import com.sdust.news.base.BasePager;
import com.sdust.news.homepagers.NearbyServicePager;
import com.sdust.news.homepagers.NewsCenterPager;
import com.sdust.news.homepagers.SettingPager;
import com.sdust.news.view.LazyViewPager.OnPageChangeListener;
import com.sdust.news.view.MyViewPager;

public class HomeFragment extends BaseFragment {

	private View view;
	@ViewInject(R.id.view_pager)
	private MyViewPager view_pager;
	@ViewInject(R.id.main_radio)
	private RadioGroup main_radio;
	@ViewInject(R.id.rb_news_center)
	private RadioButton rb_news_center;
	@ViewInject(R.id.rb_nearby)
	private RadioButton rb_nearby;
	@ViewInject(R.id.rb_setting)
	private RadioButton rb_setting;
	private List<BasePager> lists = new ArrayList<BasePager>();

	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) lists.get(0);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		System.out.println("home");
		lists.clear();
		lists.add(new NewsCenterPager(context));
		lists.add(new NearbyServicePager(context));
		lists.add(new SettingPager(context));

		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(lists);

		view_pager.setAdapter(pagerAdapter);

		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				BasePager basePager = lists.get(position);
				basePager.initData();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_news_center:
					view_pager.setCurrentItem(0, false);
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					rb_news_center
							.setBackgroundResource(R.drawable.icon_choosen);
					rb_nearby.setBackgroundResource(R.drawable.icon_unchoosen);
					rb_setting.setBackgroundResource(R.drawable.icon_unchoosen);
					break;

				case R.id.rb_nearby:
					view_pager.setCurrentItem(1, false);
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					rb_news_center
							.setBackgroundResource(R.drawable.icon_unchoosen);
					rb_nearby.setBackgroundResource(R.drawable.icon_choosen);
					rb_setting.setBackgroundResource(R.drawable.icon_unchoosen);
					break;
				case R.id.rb_setting:
					view_pager.setCurrentItem(2, false);
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					rb_news_center
							.setBackgroundResource(R.drawable.icon_unchoosen);
					rb_nearby.setBackgroundResource(R.drawable.icon_unchoosen);
					rb_setting.setBackgroundResource(R.drawable.icon_choosen);
					break;
				}
			}
		});
		main_radio.check(R.id.rb_news_center);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_home, null);
		ViewUtils.inject(this, view);
		return view;
	}

	private class ViewPagerAdapter extends PagerAdapter {

		private List<BasePager> pages;

		public ViewPagerAdapter(List<BasePager> lists) {
			this.pages = lists;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((MyViewPager) container).removeView(pages.get(position)
					.getRootView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((MyViewPager) container).addView(
					pages.get(position).getRootView(), 0);
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
