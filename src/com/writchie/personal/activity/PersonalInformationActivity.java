package com.writchie.personal.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.writchie.R;
import com.writchie.common.service.WebViewService;
import com.writchie.framework.activity.BaseActivity;
import com.writchie.framework.utils.StringUtil;

public class PersonalInformationActivity extends BaseActivity {
	private LinearLayout  hotArea;
	private String url;
	private WebViewService webViewService;
	private WebView webview;
	private EditText etUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_information);
		initHeader();
		etUrl=(EditText)this.findViewById(R.id.editTextUrls);
		Button goBtn=(Button)this.findViewById(R.id.go);
		hotArea=(LinearLayout)this.findViewById(R.id.hotArea);
		webview=(WebView)this.findViewById(R.id.webview);
		url="http://m.hao123.com";
		webViewService=new WebViewService(webview);
		webViewService.initWebveiw();
		webViewService.loadURL(url);
		goBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=etUrl.getText()+"";
				if(StringUtil.isEmpty(url)){
					url="http://m.hao123.com";
				}
				if(!url.contains("http://")){
					url="http://"+url;
				}
				webViewService.loadURL(url);
				hotArea.setVisibility(View.GONE);
			}
		});
	}

	
	private void initHeader() {
		Button headerLeftBtn=(Button)this.findViewById(R.id.header_left_btn);
		headerLeftBtn.setText(getString(R.string.back));
		headerLeftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Button headerRigthBtn=(Button)this.findViewById(R.id.header_right_btn);
		headerRigthBtn.setVisibility(View.GONE);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.personal_information, menu);
		return true;
	}

}
