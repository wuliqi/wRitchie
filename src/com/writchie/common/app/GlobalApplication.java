package com.writchie.common.app;

import android.app.Application;
import android.content.Context;
/**
 * ȫ��Application��
 * @author wRitchie
 *
 */
public class GlobalApplication extends Application {
	public static Context appContext;
	public String baiduGpsInfo;//�ٶȵ�ͼ��λ��
	

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
