package com.wanglei.mylover.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanglei.mylover.Constants;
import com.wanglei.mylover.R;
import com.wanglei.mylover.base.BaseFragment;

public class FindFragment extends BaseFragment implements OnClickListener {

	private View view;
	private ViewPager vPager = null;
	/**
	 * 代表选项卡下的下划线的imageview
	 */
	private ImageView cursor = null;
	/**
	 * 选项卡下划线长度
	 */
	private static int lineWidth = 0;

	/**
	 * 偏移量 （手机屏幕宽度/3-选项卡长度）/2
	 */
	private static int offset = 0;

	/**
	 * 选项卡总数
	 */
	private static final int TAB_COUNT = 3;
	/**
	 * 当前显示的选项卡位置
	 */
	private int current_index = 0;

	/**
	 * 选项卡标题
	 */
	private TextView comment, notification, zan;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.story, null);
		vPager = (ViewPager) view.findViewById(R.id.story_viewpager);
		comment = (TextView) view.findViewById(R.id.story_comment);
		notification = (TextView) view.findViewById(R.id.story_notification);
		zan = (TextView) view.findViewById(R.id.story_zan);
		comment.setOnClickListener(this);
		notification.setOnClickListener(this);
		zan.setOnClickListener(this);
		initImageView();
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.story_comment:

			break;

		case R.id.story_notification:

			break;
		case R.id.story_zan:

			break;
		}

	}

	@Override
	public void initData(Bundle savedInstanceState) {
		final TextView[] titles = { comment, notification, zan };
		vPager.setAdapter(new FragmentPagerAdapter(Constants.fa
				.getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return TAB_COUNT;
			}

			@Override
			public Fragment getItem(int index)// 直接创建fragment对象并返回
			{
				switch (index) {
				case 0:
					return new CommentFragment();
				case 1:
					return new NotificationFragment();
				case 2:
					return new ZanFragment();
				}
				return null;
			}
		});
		vPager.setOnPageChangeListener(new OnPageChangeListener() {
			int one = offset * 2 + lineWidth;// 页卡1 -> 页卡2 偏移量

			@Override
			public void onPageSelected(int index)// 设置标题的颜sè以及下划线的移动效果
			{
				Animation animation = new TranslateAnimation(one
						* current_index, one * index, 0, 0);
				animation.setFillAfter(true);
				animation.setDuration(300);
				cursor.startAnimation(animation);
				/*
				 * titles[current_index].setTextColor(Color.BLACK);
				 * titles[index].setTextColor(Color.RED);
				 */
				current_index = index;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int index) {
			}
		});
	}

	private void initImageView() {
		cursor = (ImageView) view.findViewById(R.id.cursor);
		// 获取图片宽度
		lineWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.line).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		Constants.fa.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕宽度
		int screenWidth = dm.widthPixels;
		Matrix matrix = new Matrix();
		offset = (int) ((screenWidth / (float) TAB_COUNT - lineWidth) / 2);
		matrix.postTranslate(offset, 0);
		// 设置初始位置
		cursor.setImageMatrix(matrix);
	}
}
