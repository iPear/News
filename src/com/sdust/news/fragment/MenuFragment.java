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
 * 创建日期：2015-3-26 下午1:42:28
 * 
 * 描 述：
 * 		MenuFragment
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdust.news.MainActivity;
import com.sdust.news.R;
import com.sdust.news.base.BaseFragment;
import com.sdust.news.base.MyBaseAdapter;

public class MenuFragment extends BaseFragment {
	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center;

	@Override
	public void initData(Bundle savedInstanceState) {
		lv_menu_news_center.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				menuListAdatper.setSelcetedPosition(position);
				slidingMenu.toggle();

				((MainActivity) context).getHomeFragment().getNewsCenterPager()
						.switchFragment(position);
			}
		});
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.layout_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	private List<String> lists = new ArrayList<String>();
	private MenuListAdatper menuListAdatper;

	public void initMenu(List<String> menuLists) {
		lists.clear();
		lists.addAll(menuLists);

		if (menuListAdatper == null) {
			menuListAdatper = new MenuListAdatper(context, lists);
			lv_menu_news_center.setAdapter(menuListAdatper);
		} else {
			menuListAdatper.notifyDataSetChanged();
		}

	}

	private class MenuListAdatper extends MyBaseAdapter<String, ListView> {
		public MenuListAdatper(Context context, List<String> lists) {
			super(context, lists);
		}

		private int mSelcetedPosition = 0;

		public void setSelcetedPosition(int position) {
			this.mSelcetedPosition = position;
			menuListAdatper.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.layout_item_menu,
						null);
			}

			ImageView iv_menu_item = (ImageView) convertView
					.findViewById(R.id.iv_menu_item);
			TextView tv_menu_item = (TextView) convertView
					.findViewById(R.id.tv_menu_item);
			if (position == mSelcetedPosition) {
				convertView
						.setBackgroundResource(R.drawable.menu_item_bg_select);
				tv_menu_item.setTextColor(context.getResources().getColor(
						R.color.red));
				iv_menu_item.setBackgroundResource(R.drawable.menu_arr_select);
			} else {
				convertView.setBackgroundResource(R.drawable.transparent);
				tv_menu_item.setTextColor(context.getResources().getColor(
						R.color.white));
				iv_menu_item.setBackgroundResource(R.drawable.menu_arr_normal);
			}
			tv_menu_item.setText(lists.get(position));
			return convertView;
		}

	}

}
