package com.sdust.news;

/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-3-18 上午10:07:34
 * 
 * 描 述：
 * 		引导界面
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GuideActivity extends Activity implements OnClickListener {

	private ViewPager view_pager;
	private ImageView iv_guidebar_1;
	private ImageView iv_guidebar_2;
	private ImageView iv_guidebar_3;
	private ImageView iv_guidebar_4;
	private ImageButton ib_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);

		iv_guidebar_1 = (ImageView) findViewById(R.id.iv_guidebar_1);
		iv_guidebar_2 = (ImageView) findViewById(R.id.iv_guidebar_2);
		iv_guidebar_3 = (ImageView) findViewById(R.id.iv_guidebar_3);
		iv_guidebar_4 = (ImageView) findViewById(R.id.iv_guidebar_4);
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		ib_start = (ImageButton) findViewById(R.id.ib_start);

		ib_start.setOnClickListener(this);
		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 3) {
					ib_start.setVisibility(View.VISIBLE);
					iv_guidebar_1
							.setBackgroundResource(R.drawable.guide_bar_no);
					iv_guidebar_2
							.setBackgroundResource(R.drawable.guide_bar_no);
					iv_guidebar_3
							.setBackgroundResource(R.drawable.guide_bar_no);
					iv_guidebar_4.setBackgroundResource(R.drawable.guide_bar_4);
				} else {
					switch (arg0) {
					case 0:
						iv_guidebar_1
								.setBackgroundResource(R.drawable.guide_bar_1);
						iv_guidebar_2
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_3
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_4
								.setBackgroundResource(R.drawable.guide_bar_no);
						break;
					case 1:
						iv_guidebar_1
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_2
								.setBackgroundResource(R.drawable.guide_bar_2);
						iv_guidebar_3
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_4
								.setBackgroundResource(R.drawable.guide_bar_no);
						break;
					case 2:
						iv_guidebar_1
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_2
								.setBackgroundResource(R.drawable.guide_bar_no);
						iv_guidebar_3
								.setBackgroundResource(R.drawable.guide_bar_3);
						iv_guidebar_4
								.setBackgroundResource(R.drawable.guide_bar_no);
						break;

					default:
						break;
					}
					ib_start.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		List<View> lists = new ArrayList<View>();

		ImageView imageView1 = new ImageView(this);
		imageView1.setBackgroundResource(R.drawable.guide_test);
		ImageView imageView2 = new ImageView(this);
		imageView2.setBackgroundResource(R.drawable.guide_test);
		ImageView imageView3 = new ImageView(this);
		imageView3.setBackgroundResource(R.drawable.guide_test);
		ImageView imageView4 = new ImageView(this);
		imageView3.setBackgroundResource(R.drawable.guide_test);

		lists.add(imageView1);
		lists.add(imageView2);
		lists.add(imageView3);
		lists.add(imageView4);

		ViewPagerAdapter adapter = new ViewPagerAdapter(lists);
		view_pager.setAdapter(adapter);
	}

	public class ViewPagerAdapter extends PagerAdapter {
		private List<View> pages = null;

		public ViewPagerAdapter(List<View> lists) {
			this.pages = lists;
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(pages.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(pages.get(position), 0);
			return pages.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
