package com.sdust.news.newspagers;

/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-4-10 上午9:09:06
 * 
 * 描 述：
 * 		NEWS Item
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdust.news.DetailActivity;
import com.sdust.news.R;
import com.sdust.news.adapter.NewsAdapter;
import com.sdust.news.base.BasePager;
import com.sdust.news.bean.NewsCategory;
import com.sdust.news.bean.NewsCategory.News;
import com.sdust.news.bean.NewsCategory.TopNews;
import com.sdust.news.utils.CommonUtil;
import com.sdust.news.utils.GsonUtils;
import com.sdust.news.utils.SdustApi;
import com.sdust.news.view.RollViewPager;
import com.sdust.news.view.pullrefreshview.PullToRefreshBase;
import com.sdust.news.view.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.sdust.news.view.pullrefreshview.PullToRefreshListView;

public class NewsItem extends BasePager {
	private String url;
	private List<View> dotLists;
	private View top_view;
	@ViewInject(R.id.dots_ll)
	private LinearLayout dots_ll;
	private List<String> imageViewUrlLists;
	private List<String> titleLists;
	@ViewInject(R.id.top_news_title)
	private TextView top_news_title;
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout top_news_viewpager;
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrlv;
	private RollViewPager mRollViewPager;

	private List<News> newsLists = new ArrayList<News>();
	private NewsAdapter adapter;
	private String moreUrl;

	public NewsItem(Context context, String url) {
		super(context);
		this.url = url;
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.fragment_item_news, null);
		top_view = View.inflate(context, R.layout.layout_roll_view, null);

		ViewUtils.inject(this, view);
		ViewUtils.inject(this, top_view);
		// 上拉
		ptrlv.setPullLoadEnabled(false);
		// 滚动
		ptrlv.setScrollLoadEnabled(true);
		ptrlv.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					private News news;

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						if (ptrlv.getRefreshableView().getHeaderViewsCount() > 0) {
							news = newsLists.get(position - 1);
						} else {
							news = newsLists.get(position);
						}

						if (!news.is_read) {
							news.is_read = true;
						}

						Intent intent = new Intent(context,
								DetailActivity.class);
						intent.putExtra("url", news.url);
						context.startActivity(intent);
						adapter.notifyDataSetChanged();
					}

				});

		ptrlv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewData(url, true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewData(moreUrl, false);
			}

		});
		ptrlv.setLastUpdatedLabel(CommonUtil.getStringDate());
		return view;
	}

	@Override
	public void initData() {
		getNewData(url, true);
	}

	private void getNewData(String url, final boolean is_refresh) {
		loadData(HttpMethod.GET, SdustApi.BASE_URL + url, null,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d(responseInfo.result);
						processData(responseInfo.result, is_refresh);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}

	protected void processData(String result, boolean is_refresh) {
		NewsCategory newsCategory = GsonUtils.json2Bean(result,
				NewsCategory.class);

		if (newsCategory.retcode == 200) {

			if (is_refresh) {
				if (newsCategory.data.topnews != null) {

					// 初始点
					initDot(newsCategory.data.topnews.size());
					// 图片url
					imageViewUrlLists = new ArrayList<String>();
					// 新闻标题
					titleLists = new ArrayList<String>();
					// 初始化
					for (TopNews topnews : newsCategory.data.topnews) {
						imageViewUrlLists.add(topnews.topimage);
						titleLists.add(topnews.title);
					}
					mRollViewPager = new RollViewPager(context, dotLists,
							new RollViewPager.OnPagerClickListener() {

								@Override
								public void onPagerClickListener(int postion) {

									Toast.makeText(context, "xxxxxxxx",
											Toast.LENGTH_SHORT).show();
								}
							});

					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);

					mRollViewPager.setLayoutParams(layoutParams);
					mRollViewPager.setImageUrlLists(imageViewUrlLists);
					mRollViewPager.setTitleLists(top_news_title, titleLists);
					mRollViewPager.startRoll();

					top_news_viewpager.addView(mRollViewPager);

				}
			}

			moreUrl = newsCategory.data.more;

			if (ptrlv.getRefreshableView().getHeaderViewsCount() < 1) {
				ptrlv.getRefreshableView().addHeaderView(top_view);
			}

			if (newsCategory.data.news != null) {

				if (is_refresh) {
					newsLists.clear();
					newsLists.addAll(newsCategory.data.news);
				} else {
					newsLists.addAll(newsCategory.data.news);
				}

				if (adapter == null) {
					adapter = new NewsAdapter(context, newsLists);
					ptrlv.getRefreshableView().setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}

				if (TextUtils.isEmpty(moreUrl)) {
					ptrlv.setHasMoreData(false);
				} else {
					ptrlv.setHasMoreData(true);
				}

				// 加载完成
				ptrlv.onPullDownRefreshComplete();
				// 刷新完成
				ptrlv.onPullUpRefreshComplete();

			}

		}

	}

	// 初始点
	private void initDot(int size) {
		dotLists = new ArrayList<View>();
		dots_ll.removeAllViews();
		for (int i = 0; i < size; i++) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					CommonUtil.dip2px(context, 6),
					CommonUtil.dip2px(context, 6));

			View m = new View(context);

			params.setMargins(5, 0, 5, 0);

			m.setLayoutParams(params);

			if (i == 0) {
				m.setBackgroundResource(R.drawable.dot_focus);
			} else {
				m.setBackgroundResource(R.drawable.dot_normal);
			}

			dotLists.add(m);
			dots_ll.addView(m);
		}
	}

}
