package com.wanglei.mylover.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.loginorregister.LoginActivity;
import com.example.loginorregister.RegisterActivity;
import com.wanglei.mylover.R;

public class LoginOrRegisterActivity extends Activity implements
		OnClickListener {

	private TextView login;
	private TextView register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setContentView(R.layout.loginorregisterlogin);
		login = (TextView) findViewById(R.id.user_login);
		register = (TextView) findViewById(R.id.user_register);
		login.setOnClickListener(this);
		register.setOnClickListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.user_login:
				Intent it=new Intent(this,LoginActivity.class);
				startActivity(it);
				finish();
			break;
			
			case R.id.user_register:
				Intent intent=new Intent(this,RegisterActivity.class);
				startActivity(intent);
			break;
		}

	}

}
