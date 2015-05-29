package com.example.loginorregister;

import com.wanglei.mylover.R;
import com.wanglei.mylover.utils.SharePrefUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
		private ImageView im_back,im_end,im_getsms;
		private FrameLayout fl_one,fl_two;
		private EditText et_name,et_word;
		private String name=null,word=null;
		private LinearLayout ly_phone,ly_sms;
		private TextView tv_phone,tv_sms;
		//注册Activity
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_register);
			im_back = (ImageView) findViewById(R.id.register_back);
			im_back.setOnClickListener(this);
			fl_one = (FrameLayout) findViewById(R.id.fl_one);
			fl_two = (FrameLayout) findViewById(R.id.fl_two);
			//邮箱注册
			et_name = (EditText) findViewById(R.id.register_youx_name);
			et_word = (EditText) findViewById(R.id.register_youx_word);
			
			im_end = (ImageView) findViewById(R.id.register_end);
			im_end.setOnClickListener(this);
			
			ly_phone = (LinearLayout) findViewById(R.id.register_phone);
			ly_sms = (LinearLayout) findViewById(R.id.register_sms);
			ly_phone.setOnClickListener(this);
			ly_sms.setOnClickListener(this);
			
			tv_phone = (TextView) findViewById(R.id.register_tv_phone);
			tv_sms  = (TextView) findViewById(R.id.register_tv_sms);
			
		}
		@SuppressWarnings("unused")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_back:
				finish();
				break;
			case R.id.register_phone:
				tv_phone.setTextColor(Color.BLUE);
				tv_sms.setTextColor(Color.WHITE);
				fl_one.setVisibility(View.VISIBLE);
				fl_two.setVisibility(View.GONE);
				break;
			case R.id.register_sms:
				tv_phone.setTextColor(Color.WHITE);
				tv_sms.setTextColor(Color.BLUE);
				fl_one.setVisibility(View.GONE);
				fl_two.setVisibility(View.VISIBLE);
				break;
				
			case R.id.register_end:
				name = et_name.getText().toString();
				word = et_word.getText().toString();
				if(name!=null&&word!=null){
					String RegisterName1=SharePrefUtil.getString(this, "RegisterName1", null);
					System.out.println("----->>>"+RegisterName1);
					if(RegisterName1!=null){
						SharePrefUtil.saveString(this, "RegisterName2", name);
						SharePrefUtil.saveString(this, "RegisterWord2", word);
					}else {
						SharePrefUtil.saveString(this, "RegisterName1", name);
						SharePrefUtil.saveString(this, "RegisterWord1", word);
					}
					Toast.makeText(this, "注册成功", 0).show();
					Intent intent = new Intent(this,LoginActivity.class);
					startActivity(intent);
					RegisterActivity.this.finish();
				}else {
					Toast.makeText(this, "邮箱或者密码不能为空", 0).show();
				}
				break;
			}
		}
}
