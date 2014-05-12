package com.writchie.framework.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.tencent.tauth.Tencent;
import com.writchie.common.app.GlobalApplication;
import com.writchie.common.conf.Constants;

/**
 * 公共的基础Activity父类
 * 
 * @author wRitchie
 * 
 */
public class BaseActivity extends Activity {
	public SharedPreferences sharedPreferences;
	public GlobalApplication app;
	public Tencent mTencent;//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (sharedPreferences == null) {
			sharedPreferences = getSharedPreferences(
					Constants.PREFER_FILE_NAME, Context.MODE_PRIVATE);
		}
		app = (GlobalApplication) getApplication();
		mTencent = Tencent.createInstance(Constants.APP_ID,
				this.getApplicationContext());
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	// 获取当前版本
	public int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
