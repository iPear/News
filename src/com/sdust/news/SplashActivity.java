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
 * 创建日期：2015-3-18 上午10:03:43
 * 
 * 描 述：
 * 		广告展示 
 * 修订历史：
 * 
 * ===================================================================
 */
import java.util.Timer;
import java.util.TimerTask;

import com.sdust.news.utils.SPUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private ImageView iv_splash_ad;
	private ImageButton ib_jump;
	private ImageView iv_splash_logo;
	private TextView tv_splash_com;

	private boolean isJump = true;

	private boolean test = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		iv_splash_ad = (ImageView) findViewById(R.id.iv_splash_ad);
		ib_jump = (ImageButton) findViewById(R.id.ib_jump);
		iv_splash_logo = (ImageView) findViewById(R.id.iv_splash_logo);
		tv_splash_com = (TextView) findViewById(R.id.tv_splash_com);

		init();
	}

	/**
	 * Init View
	 */
	private void init() {
		// TODO
		if (test) {
			iv_splash_ad.setBackgroundResource(R.drawable.splash_ad);
		}
		if (!test) {
			iv_splash_logo.setBackgroundResource(R.drawable.splash_logo);
		}
		tv_splash_com.setTextColor(0xff999999);

		showAD();

		ib_jump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				jumpTo();
				isJump = false;
			}
		});

		TimerTask task = new TimerTask() {
			public void run() {
				if (isJump) {
					jumpTo();
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 2000);
	}

	/**
	 * Show AD
	 */
	private void showAD() {
		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0f);
		animation.setDuration(2000);
		iv_splash_ad.startAnimation(animation);
		updateAD();
	}

	/**
	 * Jump To
	 */
	private void jumpTo() {
		if (SPUtil.getBoolean(SplashActivity.this, "is_first", false)) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		} else {
			SPUtil.saveBoolean(SplashActivity.this, "is_first", true);
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
			finish();
		}
	}

	/**
	 * Update AD
	 */
	private void updateAD() {
		// TODO Auto-generated method stub
	}
}
