package com.wanglei.mylover.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.wanglei.mylover.R;
import com.wanglei.mylover.bean.PhotoUpImageItem;

public class SelectedImagesActivity extends Activity implements OnClickListener {

	private TextView back, ok;
	private ArrayList<PhotoUpImageItem> arrayList;
	private ImageView iv;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	// 窗体管理者
	private WindowManager wm;
	private Point outSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.selected_images_grid);
		imageLoader = ImageLoader.getInstance();
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		outSize = new Point();
		// 使用DisplayImageOption.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.album_default_loading_pic)
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.album_default_loading_pic)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.album_default_loading_pic)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.bitmapConfig(Config.ARGB_8888)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build(); // 创建配置过的DisplayImageOption对象
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		init();
		setclickListener();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		iv = (ImageView) findViewById(R.id.selected_iv);
		back = (TextView) findViewById(R.id.back);
		ok = (TextView) findViewById(R.id.sure);
		arrayList = (ArrayList<PhotoUpImageItem>) getIntent()
				.getSerializableExtra("selectIma");
		Log.i("wl", "path:" + arrayList.get(0).getImagePath());
		
		/*@SuppressWarnings("deprecation")
		int windowWidth = wm.getDefaultDisplay().getWidth();
		@SuppressWarnings("deprecation")
		int windowHeight = wm.getDefaultDisplay().getHeight();

		// 2.计算出来图片的宽高.
		BitmapFactory.Options opts = new Options();
		// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(arrayList.get(0).getImagePath(), opts);
		int bitmapHeight = opts.outHeight;
		int bitmapWidth = opts.outWidth;

		if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
			int scaleX = bitmapWidth / windowWidth;
			int scaleY = bitmapHeight / windowHeight;
			if (scaleX > scaleY) {// 按照水平方向的比例缩放
				opts.inSampleSize = scaleX;
			} else {// 按照竖直方向的比例缩放
				opts.inSampleSize = scaleY;
			}

		} else {// 如果图片比手机屏幕小 不去缩放了.
			opts.inSampleSize = 1;
		}
		// 让位图工厂真正的去解析图片
		opts.inJustDecodeBounds = false;
		Bitmap bitmap2 = BitmapFactory.decodeFile(arrayList.get(0).getImagePath(), opts);
		iv.setImageBitmap(bitmap2);*/

		imageLoader.displayImage("file://" + arrayList.get(0).getImagePath(),
				iv, options);
	}

	private void setclickListener() {
		back.setOnClickListener(this);
		ok.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			if (arrayList.size() > 0) {
				arrayList.clear();
				arrayList = null;
			}
			finish();
			break;
		case R.id.sure:
			Toast.makeText(SelectedImagesActivity.this, "上传等操作",
					Toast.LENGTH_LONG).show();
			break;
		}
	}

}
