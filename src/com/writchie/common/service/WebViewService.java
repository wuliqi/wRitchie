package com.writchie.common.service;

import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;

public class WebViewService {//22  13  7
	WebView webView;
	
	public WebViewService(WebView webView) {
		super();
		this.webView = webView;
	}

	public void initWebveiw() {
		WebSettings webs = webView.getSettings();
		webs.setJavaScriptEnabled(true);
		webs.setSaveFormData(false);
		webs.setSavePassword(false);
		webs.setSupportZoom(false);
		webs.setLoadWithOverviewMode(true);
		webs.setTextSize(TextSize.SMALLER);
		webs.setAppCacheEnabled(true);
		webView.setBackgroundColor(0);

	}

	public boolean loadData(String data) {
		webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
		return true;
	}

	public boolean loadURL(String url) {
		webView.loadUrl(url);
		return true;
	}

}
