package com.example.loginorregister;
import com.wanglei.mylover.MainActivity;
import com.wanglei.mylover.R;
import com.wanglei.mylover.utils.SharePrefUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText et_zh, et_mimi;// 账号,密码
	private LinearLayout ly_back;// 返回按钮
	private ImageView login_im;// 登陆按钮
	private TextView tv_register, tv_wang;// 注册按钮,忘记密码
	private String name,word;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}
	private void init() {
		ly_back = (LinearLayout) findViewById(R.id.login_imback);
		ly_back.setOnClickListener(this);
		tv_register = (TextView) findViewById(R.id.login_tv_register);
		tv_register.setOnClickListener(this);
		et_zh = (EditText) findViewById(R.id.login_et_zh);
		et_mimi = (EditText) findViewById(R.id.login_et_mimi);
		login_im = (ImageView) findViewById(R.id.login_im_login);
		login_im.setOnClickListener(this);
		tv_wang = (TextView) findViewById(R.id.login_tv_wang);
		tv_wang.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_imback:
			finish();
			break;
		case R.id.login_tv_register:
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
			break;
		case R.id.login_im_login:
			name = et_zh.getText().toString();
			word = et_mimi.getText().toString();
			String RegisterName1=SharePrefUtil.getString(this, "RegisterName1", null);
			String RegisterWord1=SharePrefUtil.getString(this, "RegisterWord1", null);
			String RegisterName2=SharePrefUtil.getString(this, "RegisterName2", null);
			String RegisterWord2=SharePrefUtil.getString(this, "RegisterWord2", null);
			//存在注册,测试存储两个注册账号
			if(!name.equals("")&&!word.equals("")){
				boolean NAME_ONE=name.equals(RegisterName1)&&word.equals(RegisterWord1);
				boolean NAME_TWO=name.equals(RegisterName2)&&word.equals(RegisterWord2);
				if(NAME_ONE||NAME_TWO){
//					SharePrefUtil.saveString(this, "name", name);
//					SharePrefUtil.saveString(this, "word", word);
					SharePrefUtil.saveBoolean(this, "islogin", true);
					finish();
					Toast.makeText(this, "登录成功", 0).show();
					Intent intent2 = new Intent(this,MainActivity.class);
					startActivity(intent2);
				}else {
					Toast.makeText(this, "账号不存在，请先注册账号", 0).show();
				}
			}else {
				Toast.makeText(this, "账号或者密码不能为空", 0).show();
			}
			break;
		case R.id.login_tv_wang:
			Toast.makeText(this, "暂时不实现", 0).show();
			break;
		}
		
	}
}
