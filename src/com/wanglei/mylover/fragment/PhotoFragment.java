package com.wanglei.mylover.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.wanglei.mylover.R;
import com.wanglei.mylover.activity.SelectedImagesActivity;
import com.wanglei.mylover.adapter.AlbumItemAdapter;
import com.wanglei.mylover.base.BaseFragment;
import com.wanglei.mylover.bean.PhotoUpImageBucket;
import com.wanglei.mylover.bean.PhotoUpImageItem;
import com.wanglei.mylover.utils.PhotoUpAlbumHelper;
import com.wanglei.mylover.utils.PhotoUpAlbumHelper.GetAlbumList;

public class PhotoFragment extends BaseFragment implements OnClickListener {
	private PhotoUpAlbumHelper photoUpAlbumHelper;
	private List<PhotoUpImageBucket> list;

	private GridView gridView;
	private TextView ok;
	private PhotoUpImageBucket photoUpImageBucket;
	private ArrayList<PhotoUpImageItem> selectImages;
	private AlbumItemAdapter adapter;
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.photo, null);
		return view;
		
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		loadData();//将手机分类别存入list集合
		//init();
		//setListener();
		
	}

	private void loadData(){
		photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
		photoUpAlbumHelper.init(ct);
		photoUpAlbumHelper.setGetAlbumList(new GetAlbumList() {

			@Override
			public void getAlbumList(List<PhotoUpImageBucket> list) {
				
				PhotoFragment.this.list = list;
				Log.i("wl","list:"+PhotoFragment.this.list.size());
				init();
				setListener();
			}
		});
		photoUpAlbumHelper.execute(false);
	}
	
	private void init(){
		gridView = (GridView) view.findViewById(R.id.album_item_gridv);
		//back = (TextView) view.findViewById(R.id.back);
		ok = (TextView) view.findViewById(R.id.sure);
		selectImages = new ArrayList<PhotoUpImageItem>();
		
		photoUpImageBucket =list.get(3);
		adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(),ct);
		gridView.setAdapter(adapter);
	}
	private void setListener(){
		//back.setOnClickListener(this);
		ok.setOnClickListener(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectImages.clear();
				selectImages.add(photoUpImageBucket.getImageList().get(position));
				Intent intent = new Intent(ct,SelectedImagesActivity.class);
				intent.putExtra("selectIma", selectImages);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		/*case R.id.back:
			getActivity().finish();
			break;
		case R.id.sure:
			Intent intent = new Intent(ct,SelectedImagesActivity.class);
			intent.putExtra("selectIma", selectImages);
			startActivity(intent);
			break;*/
		}
	}
	
}
