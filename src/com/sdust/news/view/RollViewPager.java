package com.sdust.news.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sdust.news.R;

/**
 * 封装viewpager需要的数据 1 需要的点的集合 2 需要服务器动态传递图片的url地址集合 3 需要传递文字的描述 4 textView传递过来 5
 * 自己封装一个可以跳动的方法
 */
@SuppressLint("HandlerLeak")
public class RollViewPager extends ViewPager {

	public RollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RollViewPager(Context context) {
		super(context);
	}

	private Context context;
	private List<View> dotLists;

	public interface OnPagerClickListener {

		public void onPagerClickListener(int postion);

	}

	private OnPagerClickListener onPagerClickListener;

	public RollViewPager(Context context, List<View> dotLists,
			OnPagerClickListener onPagerClickListener) {
		super(context);
		this.context = context;
		this.dotLists = dotLists;

		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		taskPager = new TaskPager();

		RollViewPager.this.setOnTouchListener(new MyOnTouchListener());
		this.onPagerClickListener = onPagerClickListener;
	}

	private class MyOnTouchListener implements OnTouchListener {

		private long startTimeMillis;
		private float onTouchDownX;

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 记录X轴
				onTouchDownX = event.getX();
				// 记录时间
				startTimeMillis = System.currentTimeMillis();
				// 清空handler
				handler.removeCallbacksAndMessages(null);
				break;

			case MotionEvent.ACTION_MOVE:
				handler.removeCallbacks(taskPager);
				break;
			case MotionEvent.ACTION_UP:
				// 当手指抬起的时候记录下抬起的时间，然后在减去开始的时间，如果时间差小于500 可以判断是点击事件
				long duration = System.currentTimeMillis() - startTimeMillis;
				float onTouchUpX = event.getX();
				if (duration < 500 && onTouchUpX == onTouchDownX) {
					onPagerClickListener.onPagerClickListener(currenposition);
				}
				startRoll();

				break;
			}

			return true;
		}

	}

	private class TaskPager implements Runnable {

		@Override
		public void run() {
			currenposition = (currenposition + 1) % imageViewUrlLists.size();
			handler.obtainMessage().sendToTarget();
		}

	}

	private List<String> imageViewUrlLists;

	public void setImageUrlLists(List<String> imageViewUrlLists) {
		this.imageViewUrlLists = imageViewUrlLists;
	}

	private TextView top_news_title;
	private List<String> titleLists;
	private BitmapUtils bitmapUtils;

	public void setTitleLists(TextView top_news_title, List<String> titleLists) {
		this.top_news_title = top_news_title;
		this.titleLists = titleLists;

		if (top_news_title != null && titleLists != null
				&& titleLists.size() > 0) {
			top_news_title.setText(titleLists.get(0));
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			RollViewPager.this.setCurrentItem(currenposition);
			startRoll();
		}

	};
	private TaskPager taskPager;
	private boolean has_adapter = false;

	public void startRoll() {
		if (!has_adapter) {
			has_adapter = true;
			ViewPagerAdapter pagerAdapter = new ViewPagerAdapter();
			RollViewPager.this.setAdapter(pagerAdapter);

			RollViewPager.this
					.setOnPageChangeListener(new MyPageChangeListener());
		}

		handler.postDelayed(taskPager, 4000);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = ev.getX();
			downY = ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;

		case MotionEvent.ACTION_MOVE:

			float moveX = ev.getX();
			float moveY = ev.getY();

			if (Math.abs(downX - moveX) > Math.abs(downY - moveY)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private int currenposition = 0;
	private float downX;
	private float downY;
	private int oldposition = 0;

	private class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			currenposition = arg0;

			if (top_news_title != null && titleLists != null
					&& titleLists.size() > 0) {
				top_news_title.setText(titleLists.get(arg0));
			}
			if (dotLists != null) {
				dotLists.get(arg0).setBackgroundResource(R.drawable.dot_focus);
				dotLists.get(oldposition).setBackgroundResource(
						R.drawable.dot_normal);
			}

			oldposition = arg0;
		}

	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(context, R.layout.iv_viewpager_item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			bitmapUtils.display(image, imageViewUrlLists.get(position));
			((ViewPager) container).addView(view);
			return view;
		}

		@Override
		public int getCount() {
			return imageViewUrlLists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

}
