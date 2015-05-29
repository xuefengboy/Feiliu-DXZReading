package com.wanglei.mylover.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.wanglei.mylover.R;
public class FocusPagerAdapter extends PagerAdapter {
	public Context context;
	private ImageCache imageCache;
	private RequestQueue queue;
	private int[] arrays;
	/**
	 * 
	 * @param context
	 * @param queue2
	 * @param imageCache2
	 */
	public FocusPagerAdapter(Context context, RequestQueue queue2,
			ImageCache imageCache2) {
		this.context = context;
		this.queue = queue2;
		this.imageCache = imageCache2;
	}

	public void bindData(int[] arrays) {
		this.arrays = arrays;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(container);
	}
	@Override
	public Object instantiateItem(View container, final int position) {
		ImageView imageView = new ImageView(context);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				String content = list.get(position).getId();
//				Intent intent = new Intent(context, XQActivity.class);
//				intent.putExtra("id", content);
//				context.startActivity(intent);
			}
		});
		ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
		imageView.setScaleType(scaleType);

//		DownLoadImage
//				.downLoadImage(context, imageView,
//						"http://" + list.get(position).getImageurl(), queue,
//						imageCache);
		imageView.setImageResource(arrays[position%arrays.length]);
		((ViewPager) container).addView(imageView);
		return imageView;

	}

	@Override
	public int getCount() {
		return arrays.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
}
