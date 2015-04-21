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
 * 创建日期：2015-3-18 上午10:01:49
 * 
 * 描 述：
 * 		程序的主界面
 * 修订历史：
 * 
 * ===================================================================
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sdust.news.fragment.HomeFragment;
import com.sdust.news.fragment.MenuFragment;

public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu slidingMenu;
	private MenuFragment menu;
	private HomeFragment home;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setBehindContentView(R.layout.frame_menu);
		setContentView(R.layout.frame_main);
		System.out.println("main");
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);

		home = new HomeFragment();
		menu = new MenuFragment();

		// id fragment tab
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, home, "HOME").commit();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, menu, "MENU").commit();

		/**
		 * RightMenu
		 */
		/*
		 * RightMenuFragment rightFragment = new RightMenuFragment();
		 * sm.setSecondaryMenu(R.layout.right_menu_fram);
		 * sm.setSecondaryShadowDrawable(R.drawable.shadow);
		 * getSupportFragmentManager().beginTransaction()
		 * .replace(R.id.right_menu_frame, rightFragment).commit();
		 */
	}

	public void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		slidingMenu.toggle();
	}

	public MenuFragment getMenuFragment() {
		menu = (MenuFragment) getSupportFragmentManager().findFragmentByTag(
				"MENU");
		return menu;
	}

	public HomeFragment getHomeFragment() {
		home = (HomeFragment) getSupportFragmentManager().findFragmentByTag(
				"HOME");
		return home;
	}

}
