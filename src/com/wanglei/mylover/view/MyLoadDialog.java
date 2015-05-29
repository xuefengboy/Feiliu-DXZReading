package com.wanglei.mylover.view;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.wanglei.mylover.R;

/**
 *@author baiyuliang
 *@date 2014-07-15
 *@blog http://blog.csdn.net/baiyuliang2013
 */
public class MyLoadDialog extends Dialog {
	
	private ImageView iv_route;
	private RotateAnimation mAnim;
	private boolean cancelable = true;

	public MyLoadDialog(Context context) {
		super(context, R.style.Dialog_bocop);
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(), R.layout.my_loding_dialog_layout, null);
		setContentView(contentView);
		
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
		iv_route = (ImageView) findViewById(R.id.iv_route);
		initAnim();
		getWindow().setWindowAnimations(R.anim.alpha_in);
	}
	
	
	private void initAnim() {
		mAnim = new RotateAnimation(360, 0,Animation.RESTART, 0.5f, Animation.RESTART,0.5f);
		mAnim.setDuration(2000);
		mAnim.setRepeatCount(Animation.INFINITE);
		mAnim.setRepeatMode(Animation.RESTART);
		mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
	}

	@Override
	public void show() {
		iv_route.startAnimation(mAnim);
		super.show();
	}
	
	@Override
	public void dismiss() {
		mAnim.cancel();
		super.dismiss();
	}
	
	
	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	
}

