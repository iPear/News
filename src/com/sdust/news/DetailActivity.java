package com.sdust.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressWarnings("deprecation")
public class DetailActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.news_detail_wv)
	private WebView web_view;
	@ViewInject(R.id.loading_view)
	private View loading_view;
	private String path;

	@ViewInject(R.id.imgbtn_left)
	private ImageButton imgbtn_left;
	@ViewInject(R.id.imgbtn_right)
	private ImageButton imgbtn_right;
	@ViewInject(R.id.imgbtn_text)
	private ImageButton imgbtn_text;
	private WebSettings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		ViewUtils.inject(this);
		Intent intent = getIntent();
		path = intent.getStringExtra("url");

		initTiteBar();
		settings = web_view.getSettings();
		// 缩放
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		// 支持js
		/* settings.setJavaScriptEnabled(true); */
		web_view.setWebChromeClient(new WebChromeClient() {

		});
		web_view.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				loading_view.setVisibility(View.INVISIBLE);

			}

		});
		web_view.loadUrl(path);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
			web_view.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initTiteBar() {
		imgbtn_left.setVisibility(View.VISIBLE);
		imgbtn_left.setImageResource(R.drawable.icon_back);
		imgbtn_text.setVisibility(View.VISIBLE);
		imgbtn_text.setImageResource(R.drawable.icon_textsize);
		imgbtn_right.setVisibility(View.VISIBLE);
		imgbtn_right.setImageResource(R.drawable.icon_share);
		imgbtn_left.setOnClickListener(this);
		imgbtn_text.setOnClickListener(this);
		imgbtn_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_left:
			finish();
			break;
		case R.id.imgbtn_text:
			switchTextSize(3);
			break;
		case R.id.imgbtn_right:
			break;
		}
	}

	private void switchTextSize(int text_size) {
		switch (text_size) {
		case 1:
			settings.setTextSize(TextSize.LARGER);
			break;

		case 2:
			settings.setTextSize(TextSize.NORMAL);
			break;
		case 3:
			settings.setTextSize(TextSize.SMALLER);
			break;
		}
	}

}
