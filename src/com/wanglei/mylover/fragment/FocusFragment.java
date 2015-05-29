package com.wanglei.mylover.fragment;

import java.util.concurrent.atomic.AtomicInteger;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wanglei.mylover.R;
import com.wanglei.mylover.adapter.FocusPagerAdapter;
import com.wanglei.mylover.base.BaseFragment;
import com.wanglei.mylover.view.HorizontalListView;
import com.wanglei.mylover.view.HorizontalListViewAdapter;

public class FocusFragment extends BaseFragment {
	private ViewPager myViewPager;
	private LinearLayout mLayout, tLayout;// 布局mLayout：动态加载滑动点；tLayout：动态加载Title
	private TextView tv_add, tv_title;
	private FocusPagerAdapter adapter;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private ImageView[] im;// 动态广告点的数组
	private boolean isContinue = true;
	private int[] arrays = { R.drawable.test3, R.drawable.test2,R.drawable.test3 };
	private int[] focus = {R.drawable.focus4, R.drawable.focus1, R.drawable.focus2,R.drawable.focus3,R.drawable.focus4 };
	int pos = 0;
	int maxPos = 0;
	int currentPageScrollStatus;
	private HorizontalListView hListView;
	private HorizontalListViewAdapter hListViewAdapter;
	private View olderSelectView = null;
	private ScrollView scrollView;
	private LinearLayout addviewlayout;
	private LayoutInflater viewinflater;
	private View view1,view2;
	private ImageView focus_iv;
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.focus1, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		getActivity().getLayoutInflater();
		viewinflater = LayoutInflater.from(getActivity());
		scrollView = (ScrollView) view.findViewById(R.id.focus1_sc);
		addviewlayout = (LinearLayout) view.findViewById(R.id.focus1_add_view);
		for(int i=0;i<5;i++){
			if(i==0){
				view1 = viewinflater.inflate(
						R.layout.focus2, null);
				addviewlayout.addView(view1);
				initViewPagerViews(view1);//ViewPager
				
			}else {
				view2 = viewinflater.inflate(R.layout.focus3, null);
				focus_iv=(ImageView) view2.findViewById(R.id.focus_title);
				focus_iv.setImageResource(focus[i]);
				addviewlayout.addView(view2);
				initHListView(view2);//HListView
			}
		}
	}

	private void initHListView(View view) {
		hListView = (HorizontalListView)view.findViewById(R.id.horizon_listview);
		//String[] titles = {"怀师", "南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相"};
		final int[] ids = {R.drawable.focus_01, R.drawable.focus_02,
				R.drawable.focus_03, R.drawable.focus_04,
				R.drawable.focus_05};
		hListViewAdapter = new HorizontalListViewAdapter(getActivity(),null,ids);
		hListView.setAdapter(hListViewAdapter);
		
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
//				hListViewAdapter.setSelectIndex(position);
//				hListViewAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "我被点击了", 0).show();
				
			}
		});
	}

	private void initViewPagerViews(View view) {
		myViewPager = (ViewPager) view.findViewById(R.id.home_pager);
		mLayout = (LinearLayout) view.findViewById(R.id.layout_p);
		tLayout = (LinearLayout) view.findViewById(R.id.layout_t);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		adapter = new FocusPagerAdapter(getActivity(), null, null);
		adapter.bindData(arrays);
		myViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// 广告下面滑动点的设置
		if (arrays.length > 0) {
			im = new ImageView[arrays.length];
			for (int i = 0; i < im.length; i++) {
				im[i] = new ImageView(getActivity());
				// 设置点点之间的间距
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						20, 20);
				params.setMargins(0, 0, 12, 0);
				im[i].setLayoutParams(params);
				if (i == 0) {
					im[i].setBackgroundResource(R.drawable.d01);
					// tv_title.setText(mList.get(i).getTitle());
				} else {
					im[i].setBackgroundResource(R.drawable.d02);
				}
				mLayout.addView(im[i]);
			}
			setMaxPage(arrays.length - 1);
			myViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int arg0) {
					for (int i = 0; i < im.length; i++) {
						im[i].setBackgroundResource(R.drawable.d02);
						if (i == arg0) {
							im[i].setBackgroundResource(R.drawable.d01);

						}
					}
					//切换page设置当前position值
					setCurrentPos(arg0);
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
					if (pos == 0) {
						// 如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
						if (positionOffsetPixels == 0
								&& currentPageScrollStatus == 1) {

						}
					} else if (pos == maxPos) {
						// 已经在最后一页还想往右划
						if (positionOffsetPixels == 0
								&& currentPageScrollStatus == 1) {
							//滑动到最后一张图片，再次滑动回到第一张
			                //myViewPager.setCurrentItem(0, false);
						}
					}
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// 记录page滑动状态，如果滑动了state就是1
					currentPageScrollStatus = arg0;
				}

			});

			// 开启一个线程自动循环导航点
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if (isContinue) {
							viewHandler.sendEmptyMessage(atomicInteger.get());
							atomicOption();
						}
					}
				}
			}).start();
		}
	}

	protected void setCurrentPos(int arg0) {
		// 设置当前页的position值
		pos = arg0;

	}

	public void setMaxPage(int position) {
		// 设置最后一页的position值
		maxPos = position;
	}

	// 作为消息被传送，每个3s更换一次
	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > im.length - 1) {
			atomicInteger.getAndAdd(-(arrays.length));// 循环完后，下次从第几个开始，如果再次减1则是从第二个开始
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
	}

	// 每隔3s更换一次图片，使用handler来更新
	private final Handler viewHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			myViewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}
	};
}
