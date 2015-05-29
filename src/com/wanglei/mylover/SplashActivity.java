package com.wanglei.mylover;

import java.io.File;

import com.wanglei.mylover.R;
import com.wanglei.mylover.activity.LoginOrRegisterActivity;
import com.wanglei.mylover.utils.SharePrefUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在这里要强调一点，设置全屏的俩段代码必须在setContentView(R.layout.main) 之前，不然会报错。
		// 无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);

		if (!detect()) {
			Toast.makeText(getApplicationContext(), "请检查网络连接",
					Toast.LENGTH_LONG).show();
		}

		if (checkSDCardAvailable()) {
			File download = new File(Environment.getExternalStorageDirectory(),
					"NetEasyNews/data/download");
			File image = new File(Environment.getExternalStorageDirectory(),
					"NetEasyNews/data/image");
			if (!download.exists()) {
				download.mkdirs();
			}
			if (!image.exists()) {
				image.mkdirs();
			}
		} else {
			Toast.makeText(getApplicationContext(), "您未安装存储卡,部分功能将无法使用",
					Toast.LENGTH_LONG).show();
		}

		boolean mFirst = isFirstEnter(SplashActivity.this, SplashActivity.this
				.getClass().getName());
		if (mFirst)
			mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY, 3000);
		else
			mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 3000);
	}

	// ****************************************************************
	// 判断应用是否初次加载，读取SharedPreferences中的guide_activity字段
	// ****************************************************************
	private static final String SHAREDPREFERENCES_NAME = "my_pref";
	private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

	private boolean isFirstEnter(Context context, String className) {
		if (context == null || className == null
				|| "".equalsIgnoreCase(className))
			return false;
		String mResultStr = context.getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE).getString(
				KEY_GUIDE_ACTIVITY, "");// 取得所有类名 如 com.my.MainActivity
		if (mResultStr.equalsIgnoreCase("false"))
			return false;
		else
			return true;
	}

	// *************************************************
	// Handler:跳转至不同页面
	// *************************************************
	private final static int SWITCH_MAINACTIVITY = 1000;
	private final static int SWITCH_GUIDACTIVITY = 1001;
	private static final String ISLOGIN = "islogin";
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_MAINACTIVITY:
				// 进入主界面之前判断是否登陆过，没登陆跳转到登陆注册界面
				boolean login = SharePrefUtil.getBoolean(SplashActivity.this,
						ISLOGIN, false);
				if (!login) {// 没有登陆
					Intent mIntent = new Intent();
					mIntent.setClass(SplashActivity.this,
							LoginOrRegisterActivity.class);
					SplashActivity.this.startActivity(mIntent);
					SplashActivity.this.finish();
					return;
				}
				Intent mIntent = new Intent();
				mIntent.setClass(SplashActivity.this, MainActivity.class);
				SplashActivity.this.startActivity(mIntent);
				SplashActivity.this.finish();
				break;
			case SWITCH_GUIDACTIVITY:
				mIntent = new Intent();
				mIntent.setClass(SplashActivity.this, GuideActivity.class);
				SplashActivity.this.startActivity(mIntent);
				SplashActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * Check the SD card
	 * 
	 * @return
	 */
	private static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 检测网络状态
	 */
	private boolean detect() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

}
