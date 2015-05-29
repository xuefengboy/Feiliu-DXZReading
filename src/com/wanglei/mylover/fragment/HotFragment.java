package com.wanglei.mylover.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wanglei.mylover.Constants;
import com.wanglei.mylover.R;
import com.wanglei.mylover.adapter.NewsListAdapter;
import com.wanglei.mylover.base.BaseFragment;
import com.wanglei.mylover.listener.GetNewsFromNetListener;
import com.wanglei.mylover.view.MyLoadDialog;

public class HotFragment extends BaseFragment {

	private PullToRefreshListView mPullToRefreshView;
	private NewsListAdapter newsListAdapter;
	LinkedList<HashMap<String, Object>> newslist;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.hot, null);
		mPullToRefreshView = (PullToRefreshListView) view.findViewById(R.id.main_pull_refresh_view);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		initPullToRefreshListView();
		newslist = new LinkedList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("newslist_item_digest", "读心者读心者读心者读心者读心者读心者读心者读心者读心者读心者读心者读心者读心者");
			newslist.addFirst(hashMap);
		}
		newsListAdapter = new NewsListAdapter(ct, newslist);
		mPullToRefreshView.setAdapter(newsListAdapter);

		mPullToRefreshView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(ct, String.valueOf(position), 0).show();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initPullToRefreshListView() {
		mPullToRefreshView.setMode(Mode.PULL_FROM_START);
		mPullToRefreshView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				getNewsListFromNetByVolley(false, 1, newslist.size(), false, new GetNewsFromNetListener() {

					@Override
					public void onListener(int status) {
						switch (status) {
							case NONEWS:
								Toast.makeText(ct, "该栏目下暂时没有新闻", Toast.LENGTH_LONG).show();
								break;
							case NOMORENEWS:
								Toast.makeText(ct, "没有更多新闻啦", Toast.LENGTH_LONG).show();
								break;
						}
						newsListAdapter.notifyDataSetChanged();
						mPullToRefreshView.onRefreshComplete();
					}
				});
			}
		});
		mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				getNewsListFromNetByVolley(true, 1, newslist.size(), false, new GetNewsFromNetListener() {

					@Override
					public void onListener(int status) {
						switch (status) {
							case NONEWS:
								Toast.makeText(ct, "该栏目下暂时没有新闻", Toast.LENGTH_LONG).show();
								break;
							case NOMORENEWS:
								Toast.makeText(ct, "没有更多新闻啦", Toast.LENGTH_LONG).show();
								break;
						}
						newsListAdapter.notifyDataSetChanged();
						mPullToRefreshView.onRefreshComplete();
					}
				});
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				getNewsListFromNetByVolley(false, 1, newslist.size(), false, new GetNewsFromNetListener() {

					@Override
					public void onListener(int status) {
						switch (status) {
							case NONEWS:
								Toast.makeText(ct, "该栏目下暂时没有新闻", Toast.LENGTH_LONG).show();
								break;
							case NOMORENEWS:
								Toast.makeText(ct, "没有更多新闻啦", Toast.LENGTH_LONG).show();
								break;
						}
						newsListAdapter.notifyDataSetChanged();
						mPullToRefreshView.onRefreshComplete();
					}
				});
			}
		});
	}

	/**
	 * 获取指定类型的新闻列表
	 * @param cid 类型ID
	 * @param mNewsData2 保存新闻信息的集合
	 * @param startnid 分页
	 * @param firstTimes	是否第一次加载
	 */
	private final int NEWSCOUNT = 10; //返回新闻数目
	private final int SUCCESS = 0;//加载成功
	private final int NONEWS = 1;//该栏目下没有新闻
	private final int NOMORENEWS = 2;//该栏目下没有更多新闻
	private final int LOADERROR = 3;//加载失败
	private RequestQueue mRequestQueue;

	private void getNewsListFromNetByVolley(final boolean isPullDownToRefresh, int cid, int startnid, final Boolean firstTimes,
			final GetNewsFromNetListener mGetNewsFromNetListener) {
		if (firstTimes) {//如果是第一次，则清空集合里数据
			newslist.clear();
		}
		//请求URL和字符串
		String url = Constants.URL + "getSpecifyCategoryNews";
		String params = "startnid=" + startnid + "&count=" + NEWSCOUNT + "&cid=" + cid;
		url += "?" + params;
		mRequestQueue = Volley.newRequestQueue(ct);
		//loadDialog.show();
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {

				parserNewsList(isPullDownToRefresh, response, firstTimes, mGetNewsFromNetListener);

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				mGetNewsFromNetListener.onListener(LOADERROR);
			}
		});
		mRequestQueue.add(jr);
		mRequestQueue.start();
	}

	protected void parserNewsList(boolean isPullDownToRefresh, JSONObject response, Boolean firstTimes, GetNewsFromNetListener mGetNewsFromNetListener) {
		//获取返回码，0表示成功
		try {
			int retCode = response.getInt("ret");
			if (0 == retCode) {
				JSONObject dataObject = response.getJSONObject("data");
				//获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum > 0) {
					//获取返回新闻集合
					JSONArray news_list = dataObject.getJSONArray("newslist");
					for (int i = 0; i < news_list.length(); i++) {
						JSONObject newsObject = (JSONObject) news_list.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("nid", String.valueOf(newsObject.getInt("nid")));
						hashMap.put("newslist_item_title", newsObject.getString("title"));
						hashMap.put("newslist_item_digest", newsObject.getString("digest"));
						hashMap.put("newslist_item_source", newsObject.getString("source"));
						hashMap.put("newslist_item_ptime", newsObject.getString("ptime"));
						hashMap.put("newslist_item_comments", newsObject.getString("commentcount"));
						if (isPullDownToRefresh) {
							newslist.addFirst(hashMap);
						}
						newslist.addLast(hashMap);
					}
					mGetNewsFromNetListener.onListener(SUCCESS);
				}
				else {
					if (firstTimes) {
						mGetNewsFromNetListener.onListener(NONEWS);
					}
					else {
						mGetNewsFromNetListener.onListener(NOMORENEWS);
					}
				}
			}
			else {
				mGetNewsFromNetListener.onListener(LOADERROR);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过volley下载图片并缓存示例
	 * @author: 汪磊
	 * @param imageUrl
	 * @param iv
	 * @return: void
	 * @date: 2015-5-6 下午5:23:35
	*/

	@SuppressWarnings("unused")
	private void loadImageByVolley(String imageUrl, ImageView iv) {
		// TODO Auto-generated method stub
		RequestQueue mRequestQueue = Volley.newRequestQueue(ct);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
		ImageCache imageCache = new ImageCache() {

			@Override
			public void putBitmap(String key, Bitmap value) {
				// TODO Auto-generated method stub
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				// TODO Auto-generated method stub
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(mRequestQueue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.image_placeholder_wait_for_image,
				R.drawable.image_placeholder_wait_for_image);
		imageLoader.get(imageUrl, listener);
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			//((ViewPager) v).removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			/*View view = mListViews.get(position);
			((ViewPager) v).addView(view);
			return view;*/
			container.addView(mListViews.get(position % mListViews.size()));
			return mListViews.get(position % mListViews.size());
		}

		@Override
		public int getCount() {
			return 10000;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
}
