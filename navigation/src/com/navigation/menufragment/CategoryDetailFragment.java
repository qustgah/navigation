package com.navigation.menufragment;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.bean.Picture;
import com.navigation.dao.CommonDao;
import com.navigation.dao.GameDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;

public class CategoryDetailFragment extends BaseFragment{

	List<Map<String, Object>> lstImageItem = new ArrayList<Map<String, Object>>();
	ArrayList<Picture> pics = new ArrayList<Picture>();
	public DisplayImageOptions options;
	private AdView mAdView;
	JazzyGridView grid;
	SimpleAdapter adapter;
	private boolean isloading = false;// �ж��Ƿ������
	private boolean isScrolling = false;// �Ƿ����ڹ���
	private boolean ishas = true; // �Ƿ�������
	private String page = "1"; // ��ȡ�ڼ�ҳ����Ϣ
	String url ; //��ȡ�����������Ϸ
	int cid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.secondfragment, null);
		/**
		 * ���
		 */
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.secondfragmentad);
		mAdView = new AdView(getActivity());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		cid = (int) getActivity().getIntent().getLongExtra("cateid", 1);
		url = getResources().getString(R.string.CategoryDetail)+"?page=";
		grid = (JazzyGridView) view.findViewById(R.id.more);
		options = new DisplayImageOptions.Builder()  
	       .showStubImage(R.drawable.stub_image)          // ����ͼƬ�����ڼ���ʾ��ͼƬ  
	       .showImageForEmptyUri(R.drawable.stub_image)  // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
	       .showImageOnFail(R.drawable.stub_image)       // ����ͼƬ���ػ��������з���������ʾ��ͼƬ      
	       .cacheInMemory(true)                        // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
	       .cacheOnDisc(true)                          // �������ص�ͼƬ�Ƿ񻺴���SD����  
	       .displayer(new RoundedBitmapDisplayer(20))  // ���ó�Բ��ͼƬ  
	       .build();                                   // �������ù���DisplayImageOption����  
		grid.setTransitionEffect(JazzyHelper.FAN);
		getData();
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView game = (TextView) view.findViewById(R.id.gid);
				String gid = game.getText().toString();
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailActivity.class);
				intent.putExtra("gameid", Long.valueOf(gid));
				startActivity(intent);
			}
		});
		// �����ײ������µ�ͼƬ
		grid.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (totalItemCount <= 0) {
					return;
				}
				if (firstVisibleItem + visibleItemCount == totalItemCount) {
					if (isloading || !ishas) {
						return;
					}
					new AsyncTask<Void, String, List<Map<String, Object>>>() {
						protected List<Map<String, Object>> doInBackground(
								Void... params) {
							List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
							try {
								String data = CommonDao.getDataFromServer(url+page+"&cid="+cid);
								list = CommonDao.StringToJson(data);
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}
							return list;
						}
						protected void onPreExecute() {
							isloading = true;
							super.onPreExecute();
						}
						protected void onPostExecute(List<Map<String, Object>> result) {
							List<Picture> list = new ArrayList<Picture>();
							try {
								list = GameDao.getPictures(result);
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}
							for (Picture pic : list) {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("gid", pic.getGameid().getId());
								map.put("localimg", pic.getUrl());
								map.put("name", pic.getGameid().getName());
								lstImageItem.add(map);
							}
							adapter.notifyDataSetChanged();
							page = String.valueOf((Integer.valueOf(page) + 1));
							isloading = false;
							if (result.size() < 7 || result == null) {
								ishas = false;
							}
							super.onPostExecute(result);
						}
						protected void onProgressUpdate(String... values) {
							Toast.makeText(getActivity(), values[0],
									Toast.LENGTH_SHORT).show();
							super.onProgressUpdate(values);
						}
					}.execute();
				}
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
					isScrolling = true;
					break;
				case OnScrollListener.SCROLL_STATE_IDLE:
					isScrolling = false;
					// ��ȡ��Ļ�ϵ�һ����ʾ��λ��
					int startindex = grid.getFirstVisiblePosition();
					// ��ȡ
					int count = grid.getChildCount();
					for (int i = 0; i < count; i++) {
						int currentpostion = startindex + i;
						final Map<String, Object> pic = (Map<String, Object>) grid.getItemAtPosition(currentpostion);
						final View viewchildren = grid.getChildAt(i);
						TextView tv = (TextView) viewchildren.findViewById(R.id.localItemText);
						TextView tv2 = (TextView) viewchildren.findViewById(R.id.gid);
						ImageView iv = (ImageView) viewchildren.findViewById(R.id.localItemImage);
						String addr = "";
						String text = "";
						String id = "";
						imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
						if (pic == null) {
							addr = "";
							text = "";
							id = "";
						} else {
							URL url = null;
							try {
								url = new URL((String) pic.get("localimg"));
								addr = url.toString();
								text = (String) pic.get("name");
								id = String.valueOf(pic.get("gid"));
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}
						}
						if(tv!=null&&tv2!=null&&iv!=null){
							tv2.setText(id);
							tv.setText(text);
							imageloader.displayImage(addr, iv,options);
						}
					}
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					isScrolling = true;
					break;
				}
			}
		});
		return view;
	}
	public void getData() {
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				try {
					String data = CommonDao.getDataFromServer(url+page+"&cid="+cid);
					list = CommonDao.StringToJson(data);
				} catch (Exception e) {
					System.out.println(e.getMessage().toString());
				}
				return list;
			}
			protected void onPreExecute() {
				isloading = true;
				Toast.makeText(getActivity(), "loading...", 3).show();
				super.onPreExecute();
			}

			protected void onPostExecute(List<Map<String, Object>> result) {
				try {
					pics = GameDao.getPictures(result);
				} catch (Exception e) {
					System.out.println(e.getMessage().toString());
				}
				for (Picture pic : pics) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("gid", pic.getGameid().getId());
					map.put("localimg", pic.getUrl());
					map.put("name", pic.getGameid().getName());
					lstImageItem.add(map);
				}
				adapter = new SimpleAdapter(getActivity(), lstImageItem,
						R.layout.item_pager, new String[] { "gid", "localimg",
								"name" }, new int[] { R.id.gid,R.id.localItemImage, R.id.localItemText }) {
					public Object getItem(int position) {
						return lstImageItem.get(position);
					};
				};
				adapter.setViewBinder(new ListViewBinder());
				grid.setAdapter(adapter);
			    page = String.valueOf((Integer.valueOf(page)+1));
			    if(result.size()<8||result==null){
					ishas = false;
				}
				isloading = false;
				super.onPostExecute(result);
			}
			protected void onProgressUpdate(String... values) {
				Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
				super.onProgressUpdate(values);
			}
		}.execute();
	}

	private class ListViewBinder implements ViewBinder {
		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {

			if ((view instanceof ImageView) && (data instanceof String)) {
				ImageView imageView = (ImageView) view;
				imageView.setTag((String) data);
				imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
				imageloader.displayImage((String) data, imageView,options);
				return true;
			}
			return false;
		}
	}
} 