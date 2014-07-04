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
	private final int SPLASH_DISPLAY_LENGHT = 5000; // 延迟5秒
	// 百度定位SDK的核心类
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
		setLocationOption();// 设定定位参数
		mLocationClient.start();// 开始定位
	}
	
	/**
	 * 百度定位设置相关参数
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。
	    //option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		option.setPriority(LocationClientOption.GpsFirst);// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
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

			// 将获取的信息保存到application中 经度:Longitude,纬度:Latitude
			app.baiduGpsInfo=sb.toString();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();// 停止定位
			mLocationClient = null;
		}
	}

	public void showGPSInfo(String sb) {
		toast("百度SDK定位信息：" + sb);
		
	}


}
