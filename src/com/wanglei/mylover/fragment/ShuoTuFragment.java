package com.wanglei.mylover.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wanglei.mylover.Constants;
import com.wanglei.mylover.R;
import com.wanglei.mylover.base.BaseFragment;

public class ShuoTuFragment extends BaseFragment implements OnClickListener {

	private TextView shoutu_tv_hot, shoutu_tv_focus;
	private ViewPager mViewPager;
	private MyViewPagerAdapter adapter;
	private HotFragment mHotFragment;
	private FocusFragment mFocusFragment;
	private List<Fragment> mListViews;
	private TextView[] text;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.shuotu, null);
		// title = (TextView) view.findViewById(R.id.title_travel);
		shoutu_tv_hot = (TextView) view.findViewById(R.id.shoutu_tv_hot);
		shoutu_tv_hot.setTag(0);
		shoutu_tv_hot.setOnClickListener(this);
		shoutu_tv_focus = (TextView) view.findViewById(R.id.shoutu_tv_focus);
		shoutu_tv_focus.setTag(1);
		shoutu_tv_focus.setOnClickListener(this);
		mViewPager = (ViewPager) view.findViewById(R.id.shuotu_viewpager);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// title.setText("读心者");
		mListViews = new ArrayList<Fragment>();
		mFocusFragment = new FocusFragment();
		mHotFragment = new HotFragment();
		mListViews.add(mHotFragment);
		mListViews.add(mFocusFragment);
		adapter = new MyViewPagerAdapter(
				Constants.fa.getSupportFragmentManager(), mListViews);
		mViewPager.setAdapter(adapter);
		text = new TextView[mListViews.size()];
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < text.length; i++) {
					shoutu_tv_hot.setTextColor(Color.GRAY);
					shoutu_tv_focus.setTextColor(Color.GRAY);
				}
				switch (arg0) {
				case 0:
					shoutu_tv_hot.setTextColor(Color.WHITE);
					break;
				case 1:
					shoutu_tv_focus.setTextColor(Color.WHITE);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
		private List<Fragment> mListViews;

		public MyViewPagerAdapter(FragmentManager fm, List<Fragment> mListViews) {
			super(fm);
			this.mListViews = mListViews;
		}

		@Override
		public Fragment getItem(int position) {
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shoutu_tv_hot:
			mViewPager.setCurrentItem((Integer) shoutu_tv_hot.getTag());
			break;
		case R.id.shoutu_tv_focus:
			mViewPager.setCurrentItem((Integer) shoutu_tv_focus.getTag());
			break;

		}

	}

}
