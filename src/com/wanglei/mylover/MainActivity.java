package com.wanglei.mylover;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wanglei.mylover.adapter.FragmentTabAdapter;
import com.wanglei.mylover.fragment.FindFragment;
import com.wanglei.mylover.fragment.MessageFragment;
import com.wanglei.mylover.fragment.MineFragment;
import com.wanglei.mylover.fragment.PhotoFragment;
import com.wanglei.mylover.fragment.ShuoTuFragment;

public class MainActivity extends FragmentActivity {

	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无title            
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content);
		Constants.fa = this;
		
		fragments.add(new ShuoTuFragment());
        fragments.add(new MessageFragment());
        fragments.add(new PhotoFragment());
        fragments.add(new FindFragment());
        fragments.add(new MineFragment());
		rgs = (RadioGroup) this.findViewById(R.id.main_radio);
		FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.main_content, rgs);
		
	}

//	--------------按两次返回键退出程序------------------------------------------
	// 定义一个变量，来标识是否退出
	private static boolean isExit = false;

	static Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}
		else {
			finish();
			System.exit(0);
		}
	}

//------------------------------------------------------------------------
}
