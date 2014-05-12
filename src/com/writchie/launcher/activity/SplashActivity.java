package com.writchie.launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.writchie.R;
import com.writchie.common.conf.Constants;
import com.writchie.framework.activity.BaseActivity;
import com.writchie.login.activity.LoginActivity;

public class SplashActivity extends BaseActivity {
	private final int SPLASH_DISPLAY_LENGHT = 5000; // �ӳ�5��
	// �ٶȶ�λSDK�ĺ�����
	public LocationClient mLocationClient = null;
	public LocationListenner locationListener = new LocationListenner();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initBaiduGps();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	private void initBaiduGps() {
		mLocationClient = new LocationClient(this);
		mLocationClient.setAK(Constants.BAIDU_ACCESS_KEY);
		mLocationClient.registerLocationListener(locationListener);
		setLocationOption();// �趨��λ����
		mLocationClient.start();// ��ʼ��λ
	}
	
	/**
	 * �ٶȶ�λ������ز���
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		// ���������������������ֵС��1000��ms��ʱ������һ�ζ�λģʽ��
	    //option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);// ��ֹ���û��涨λ
		option.setPoiNumber(5); // ��෵��POI����
		option.setPoiDistance(1000); // poi��ѯ����
		option.setPoiExtraInfo(true); // �Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
		option.setPriority(LocationClientOption.GpsFirst);// ��gps���ã����һ�ȡ�˶�λ���ʱ�����ٷ�����������ֱ�ӷ��ظ��û����ꡣ���ѡ���ʺ�ϣ���õ�׼ȷ����λ�õ��û������gps�����ã��ٷ����������󣬽��ж�λ��
		mLocationClient.setLocOption(option);
	}

	public class LocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation arg0) {
			Dispose(arg0);
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			Dispose(arg0);
		}

		private void Dispose(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("{\"time\": ");
			sb.append("\"");
			sb.append(location.getTime());
			sb.append("\"");
			sb.append(",\"error code\": ");
			sb.append("\"");
			sb.append(location.getLocType());
			sb.append("\"");
			sb.append(",\"latitude\": ");
			sb.append("\"");
			sb.append(location.getLatitude());
			sb.append("\"");
			sb.append(",\"lontitude\": ");
			sb.append("\"");
			sb.append(location.getLongitude());
			sb.append("\"");
			sb.append(",\"radius\": ");
			sb.append("\"");
			sb.append(location.getRadius());
			sb.append("\"");
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append(",\"speed\" : ");
				sb.append("\"");
				sb.append(location.getSpeed());
				sb.append("\"");
				sb.append(",\"satellite\" : ");
				sb.append("\"");
				sb.append(location.getSatelliteNumber());
				sb.append("\"");
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append(",\"addr\" : ");
				sb.append("\"");
				sb.append(location.getAddrStr());
				sb.append("\"");
			}
			if (location.hasPoi()) {
				sb.append(",\"Poi:\"");
				sb.append("\"");
				sb.append(location.getPoi());
				sb.append("\"");
			} else {
				sb.append(",\"Poi\":");
				sb.append("\"");
				sb.append("noPoi information");
				sb.append("\"");
			}
			sb.append("}");
			if (Constants.DEBUG) {
				showGPSInfo(sb.toString());
				Log.i("wRitchie", sb.toString());
			}
			// LocationClientOption option = new LocationClientOption();
			// option.setOpenGps(false);
			// mLocationClient.setLocOption(option);

			// ����ȡ����Ϣ���浽application�� ����:Longitude,γ��:Latitude
			app.baiduGpsInfo=sb.toString();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();// ֹͣ��λ
			mLocationClient = null;
		}
	}

	public void showGPSInfo(String sb) {
		toast("�ٶ�SDK��λ��Ϣ��" + sb);
		
	}


}
