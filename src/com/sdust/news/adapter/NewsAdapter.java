package com.sdust.news.adapter;
/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-4-10 上午11:08:24
 * 
 * 描 述：
 * 		NEWS Adapter
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sdust.news.R;
import com.sdust.news.base.MyBaseAdapter;
import com.sdust.news.bean.NewsCategory.News;

public class NewsAdapter extends MyBaseAdapter<News, ListView> {

	private BitmapUtils bitmapUtils;

	public NewsAdapter(Context context, List<News> newsLists) {
		super(context, newsLists);
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.layout_news_item, null);
		}
		ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
		bitmapUtils.display(iv_img, lists.get(position).listimage);

		TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
		TextView tv_pub_date = (TextView) convertView
				.findViewById(R.id.tv_pub_date);

		if (lists.get(position).is_read) {
			tv_title.setTextColor(context.getResources().getColor(
					R.color.news_item_has_read_textcolor));
		} else {
			tv_title.setTextColor(context.getResources().getColor(
					R.color.news_item_no_read_textcolor));
		}

		tv_title.setText(lists.get(position).title);

		tv_pub_date.setText(lists.get(position).pubdate);
		return convertView;
	}

}
