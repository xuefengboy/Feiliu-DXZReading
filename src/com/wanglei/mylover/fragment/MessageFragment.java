package com.wanglei.mylover.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wanglei.mylover.R;
import com.wanglei.mylover.base.BaseFragment;

public class MessageFragment extends BaseFragment{

	private TextView title;
	
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.foryou, null);
		title=(TextView) view.findViewById(R.id.title_foryou);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		title.setText("发现");
	}


}
