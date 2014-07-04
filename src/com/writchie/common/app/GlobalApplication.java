package com.writchie.common.app;

import android.app.Application;
import android.content.Context;
/**
 * 全局Application类
 * @author wRitchie
 *
 */
public class GlobalApplication extends Application {
	public static Context appContext;
	public String baiduGpsInfo;//百度地图定位信
	

	@Override
	public void onCreate() {
		super.onCreate();
		appContext=this.getBaseContext();
	}

	public String getBaiduGpsInfo() {
		return baiduGpsInfo;
	}

	public void setBaiduGpsInfo(String baiduGpsInfo) {
		this.baiduGpsInfo = baiduGpsInfo;
	}

}
