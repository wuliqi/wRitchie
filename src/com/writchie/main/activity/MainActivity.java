package com.writchie.main.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;
import com.tencent.connect.UserInfo;
import com.writchie.R;
import com.writchie.common.conf.HttpUrlConstants;
import com.writchie.framework.activity.BaseActivity;
import com.writchie.framework.utils.FastJsonUtil;
import com.writchie.framework.utils.StringUtil;
import com.writchie.login.activity.LoginActivity;
import com.writchie.personal.activity.PersonalInformationActivity;

/**
 * ��ҳ����
 * 
 * @author wRitchie
 * 
 */
@SuppressLint("HandlerLeak")
public class MainActivity extends BaseActivity implements OnClickListener {
	private boolean isExit;// ���ε�����ؼ����
	private UserInfo mInfo;
	private Map<String, Object> baiduGPSMap = new HashMap<String, Object>();// �ٶȵ�ͼMap����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String qqInfo = getIntent().getStringExtra("qqInfo");
		final TextView qqInfoTv = (TextView) this.findViewById(R.id.qqInfo);
		qqInfo = app.baiduGpsInfo;
		qqInfoTv.setText(qqInfo);
		String gpsInfo = app.baiduGpsInfo;
		if (!StringUtil.isEmpty(gpsInfo)) {
			baiduGPSMap = FastJsonUtil.toMap(gpsInfo);
		}

		Button leftBtn = (Button) this.findViewById(R.id.header_left_btn);
		leftBtn.setVisibility(View.GONE);

		Button rightBtn = (Button) this.findViewById(R.id.header_right_btn);// ע��
		rightBtn.setText(getString(R.string.main_exit));

		// rightBtn.setBackgroundResource(R.drawable.logout_exit);
		rightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();
			}
		});

		System.out.println("getQQToken  AccessToken��"
				+ mTencent.getQQToken().getAccessToken() + "\nOpenId��"
				+ mTencent.getQQToken().getOpenId() + "\nAppId:"
				+ mTencent.getQQToken().getAppId());
		// TODO
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		RequestParams parames = new RequestParams();
		parames.add("access_token", mTencent.getQQToken().getAccessToken());
		parames.add("oath_consumer_key", mTencent.getQQToken().getAppId());//
		parames.add("openid", mTencent.getQQToken().getOpenId());
		parames.add("format", "json");
		asyncHttpClient.get(HttpUrlConstants.GET_USER_INFO, parames,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {

					}

					@Override
					public void onFinish() {
						super.onFinish();
					}

					@Override
					public void onSuccess(String content) {
						Log.i("wRitchie", "QQ���������û���Ϣ��" + content);
						Map<String, Object> userInfoMap = FastJsonUtil
								.toMap(content);
						initLeftMenuHeader(userInfoMap);
					}

				});

		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_menu);// ���˵�����

		// ������Ѷ
		LinearLayout linearLayout2 = (LinearLayout) this
				.findViewById(R.id.line2);
		linearLayout2.setOnClickListener(this);

		// �˳�
		LinearLayout linearLayout6 = (LinearLayout) this
				.findViewById(R.id.line6);
		linearLayout6.setOnClickListener(this);
	}

	// ���˵�ͷ����Ϣ��ʼ��
	protected void initLeftMenuHeader(Map<String, Object> userInfoMap) {
		String nickName = userInfoMap.get("nickname") + "";
		String qqPhotoUrl = userInfoMap.get("figureurl_qq_1") + "";// 40X40���ص�QQͷ��url
		TextView text = (TextView) this.findViewById(R.id.nickName);
		TextView addr = (TextView) this.findViewById(R.id.addr);
		SmartImageView qqPhoto = (SmartImageView) this
				.findViewById(R.id.qqPhoto);
		if (StringUtil.isEmpty(qqPhotoUrl)) {
			qqPhoto.setImageUrl("http://qlogo2.store.qq.com/qzone/408873941/408873941/100?1366516880");
		}else{
			qqPhoto.setImageUrl(qqPhotoUrl);
		}
		text.setText("���ã�" + nickName + "��");
		addr.setText(baiduGPSMap.get("addr") + "");

		// qqPhoto.setBackground(); TODO ��ȡͷ��
		// Bitmap bitmap = Util.getbitmap(qqPhotoUrl);
		// qqPhoto.setImageBitmap(bitmap);

	}

	// ע��
	protected void logout() {
		mTencent.logout(this);
		if (!mTencent.isSessionValid()) {
			toast(getString(R.string.main_exit_toast));
			Editor editor = sharedPreferences.edit();
			editor.remove("openid");
			editor.remove("access_token");
			editor.remove("expires_in");
			editor.commit();
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ���ؼ��¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// �˳�ϵͳ
	public void exit() {
		if (!isExit) {
			isExit = true;
			toast(getString(R.string.main_exit_app_toast));
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}

	};

	/**
	 * ���Ʋ˵������¼�
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.line2:
			Intent intent = new Intent(MainActivity.this,
					PersonalInformationActivity.class);
			startActivity(intent);
			break;
		case R.id.line6:
			//isExit = true;
			exit();
			break;
		default:
			break;
		}

	}
}