package com.writchie.login.activity;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.writchie.R;
import com.writchie.common.conf.Constants;
import com.writchie.framework.activity.BaseActivity;
import com.writchie.framework.utils.AESUtil;
import com.writchie.framework.utils.FastJsonUtil;
import com.writchie.framework.utils.MD5Util;
import com.writchie.framework.utils.StringUtil;
import com.writchie.main.activity.MainActivity;
/**
 * 用户登录
 * @author wRitchie
 *
 */
public class LoginActivity extends BaseActivity {
	private RelativeLayout loginBtn;
	String Scope = "all";
	
	
	private String openid;
	private String access_token;
	private String expires_in;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		TextView titleTv=(TextView)this.findViewById(R.id.header_title);
//		titleTv.setText(R.string.login_title);
//		Button leftBtn=(Button)this.findViewById(R.id.header_left_btn);
//		leftBtn.setVisibility(View.GONE);
//		Button rightBtn=(Button)this.findViewById(R.id.header_right_btn);
//		rightBtn.setVisibility(View.GONE);
		
		openid = sharedPreferences.getString("openid", "");
		access_token = sharedPreferences.getString("access_token", "");
		expires_in = sharedPreferences.getString("expires_in", "");
		
		loginBtn = (RelativeLayout) this.findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doLogin();
			}
		});
	}

	public void doLogin() {
		/*if(!(StringUtil.isEmpty(openid)||StringUtil.isEmpty(access_token)||StringUtil.isEmpty(expires_in))){
			// 密码解密成明文
			String md5 = MD5Util.md5(Constants.SKEY);
			String sKey = md5.substring(0, md5.length() - 16);
			openid = AESUtil.Decrypt(openid, sKey);
			access_token = AESUtil.Decrypt(access_token, sKey);
			String expires_inDecrypt = AESUtil.Decrypt(expires_in, sKey);
			long expiresIn=Long.parseLong(expires_inDecrypt);
			expires_in=(expiresIn-System.currentTimeMillis())/1000+"";
			Log.i("wRitchie","解密后：openId:"+openid+"\t accessToken:"+access_token+"\texpiresIn:"+expires_in);
			//mTencent.setOpenId(openid);
			//mTencent.setAccessToken(access_token, expires_in);
		}else{//已经登录
			Intent intent=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}*/
		
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, Scope, new IUiListener() {

				@Override
				public void onError(UiError arg0) {
					toast(getString(R.string.login_qq_error_toast));
					
				}

				@Override
				public void onCancel() {
					// cancel();
					//toast("cancel...");
				}

				@Override
				public void onComplete(Object arg0) {
					toast(getString(R.string.login_qq_success_toast));
					Log.i("wRitchie", "QQ返回："+arg0.toString());
					Map<String, Object> qqInfoMap=FastJsonUtil.toMap(arg0.toString());
					//明文加密保存    TODO
					String openId=(String) qqInfoMap.get("openid");
					String accessToken=(String) qqInfoMap.get("access_token");
					String expiresIn=String.valueOf(qqInfoMap.get("expires_in"));
					long expiresInlong=System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000;//token的失效日期
					Log.i("wRitchie","加密前：openId:"+openId+"\t accessToken:"+accessToken+"\texpiresIn:"+expiresInlong);
					Editor editor = sharedPreferences.edit();
					String md5 = MD5Util.md5(Constants.SKEY);
					String sKey = md5.substring(0, md5.length() - 16);
					String openIdEncry = AESUtil.Encrypt(openId, sKey);
					String accessTokenEncry = AESUtil.Encrypt(accessToken, sKey);
					String expiresInEncry = AESUtil.Encrypt(expiresInlong+"", sKey);
					editor.putString("openid", openIdEncry);
					editor.putString("access_token", accessTokenEncry);
					editor.putString("expires_in", expiresInEncry);
					editor.commit();
					Log.i("wRitchie","加密后：openId:"+openIdEncry+"\t accessToken:"+accessTokenEncry+"\texpiresIn:"+expiresInEncry);
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					//intent.putExtra("qqInfo", "openId:"+openId+"\t accessToken:"+accessToken+"\texpiresIn:"+expiresInlong);
					startActivity(intent);
					finish();
				}
			});
		}
	}
	

}
