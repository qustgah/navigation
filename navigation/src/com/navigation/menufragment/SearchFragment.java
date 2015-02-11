package com.navigation.menufragment; 

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.bean.Picture;
import com.navigation.common.imageLoader.AnimateFirstDisplayListener;
import com.navigation.dao.CommonDao;
import com.navigation.dao.GameDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class SearchFragment extends BaseFragment {
	private ListView gamelist ;
	private AdView mAdView;
	private List<Picture> pics = new ArrayList<Picture>();
	LayoutInflater inflater;
	GameListAdapter adapter;
	public String searchtext;
	private boolean isloading = false;//�ж��Ƿ������
	private boolean isScrolling = false;// �Ƿ����ڹ���
	private boolean ishas = true; // �Ƿ�������
	private String page = "1"; // ��ȡ�ڼ�ҳ����Ϣ
	public DisplayImageOptions options;
	String url ;
	ImageLoadingListener animateFirstListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater;
		searchtext = getActivity().getIntent().getStringExtra("searchtext");
		url =  getResources().getString(R.string.SearchByText)+"?page=";
		View view=inflater.inflate(R.layout.searchfragment, null);
		/**
		 * ���
		 */
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.searchfragmentad);
		mAdView = new AdView(getActivity());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		options = new DisplayImageOptions.Builder()  
	       .showStubImage(R.drawable.stub_image)          // ����ͼƬ�����ڼ���ʾ��ͼƬ  
	       .showImageForEmptyUri(R.drawable.stub_image)  // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
	       .showImageOnFail(R.drawable.stub_image)       // ����ͼƬ���ػ��������з���������ʾ��ͼƬ      
	       .cacheInMemory(true)                        // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
	       .cacheOnDisc(true)                          // �������ص�ͼƬ�Ƿ񻺴���SD����  
	       .displayer(new RoundedBitmapDisplayer(20))  // ���ó�Բ��ͼƬ  
	       .build(); 
		animateFirstListener = new AnimateFirstDisplayListener();// �������ù���DisplayImageOption����  
		gamelist = (ListView) view.findViewById(R.id.gamelist);
		getData();
		// �����ײ������µ�ͼƬ
		
		gamelist.setOnScrollListener(new OnScrollListener() {
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
								String data = CommonDao.getDataFromServer(url+page+"&searchText="+searchtext);
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
							List<Picture> mylist = new ArrayList<Picture>();
							try {
								mylist = GameDao.getPictures(result);
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}
							pics.addAll(mylist); 
							adapter.notifyDataSetChanged();
							page = String.valueOf((Integer.valueOf(page) + 1));
							isloading = false;
							if (result.size() < 7 || result == null) {
								ishas = false;
							}
							super.onPostExecute(result);
						}
						protected void onProgressUpdate(String... values) {
							Toast.makeText(getActivity(), values[0],Toast.LENGTH_SHORT).show();
							super.onProgressUpdate(values);
						}
					}.execute();
				}
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				switch (scrollState) {
//				case OnScrollListener.SCROLL_STATE_FLING:
//					isScrolling = true;
//					break;
//				case OnScrollListener.SCROLL_STATE_IDLE:
//					isScrolling = false;
//					// ��ȡ��Ļ�ϵ�һ����ʾ��λ��
//					int startindex = gamelist.getFirstVisiblePosition();
//					// ��ȡ
//					int count = gamelist.getChildCount();
//					for (int i = 0; i < count; i++) {
//						int currentpostion = startindex + i;
//						final Picture picture = (Picture) gamelist.getItemAtPosition(currentpostion);
//						final View viewchildren = gamelist.getChildAt(i);
//						TextView tvname = (TextView) viewchildren.findViewById(R.id.gamename);
//						TextView tv2text = (TextView) viewchildren.findViewById(R.id.gamesource);
//						TextView tv3id = (TextView) viewchildren.findViewById(R.id.gameid);
//						ImageView ivaddr = (ImageView) viewchildren.findViewById(R.id.gamephoto);
//						String addr = "";
//						String text = "";
//						String name ="";
//						String id = "";
//						imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
//						if (picture == null) {
//							addr = "";
//							text = "";
//							id = "";
//						} else {
//							URL url = null;
//							try {
//								url = new URL(picture.getUrl());
//								addr = url.toString();
//								text = picture.getGameid().getName();
//								id = String.valueOf(picture.getGameid().getId());
//							} catch (Exception e) {
//								System.out.println(e.getMessage().toString());
//							}
//						}
//						if(tv2text!=null&&tvname!=null&&ivaddr!=null&&tv3id!=null){
//							tv2text.setText(text);
//							tvname.setText(name);
//							tv3id.setText(id);
//							imageloader.displayImage(addr, ivaddr,options,animateFirstListener);
//						}
//					}
//					break;
//				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//					isScrolling = true;
//					break;
//				}
			}
		});
		gamelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				TextView game = (TextView) arg1.findViewById(R.id.gameid);
				String gid = game.getText().toString();
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailActivity.class);
				intent.putExtra("gameid", Long.valueOf(gid));
				startActivity(intent);
			}
		}); 
		return view;
	} 
	class GameListAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return pics.size() ;
		}
		@Override
		public Object getItem(int position) {
			return pics.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView ;
			ImageView imageview = null;
			TextView tv1 ;
			TextView tv2 ;
			TextView tv3;
			imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
			if (convertView == null) {
				view = inflater.inflate(R.layout.searchfragment_item, null);
			    imageview = (ImageView) view.findViewById(R.id.gamephoto);
			    tv1 = (TextView) view.findViewById(R.id.gamename);
			    tv2 = (TextView) view.findViewById(R.id.gamesource);
			    tv3 = (TextView) view.findViewById(R.id.gameid);
			} else{
				imageview = (ImageView) view.findViewById(R.id.gamephoto);
				tv1 = (TextView) view.findViewById(R.id.gamename);
			    tv2 = (TextView) view.findViewById(R.id.gamesource);
			    tv3 = (TextView) view.findViewById(R.id.gameid);
			}
			tv1.setText(pics.get(position).getGameid().getName());
			tv2.setText(pics.get(position).getGameid().getReleaseMan().getName());
			tv3.setText(String.valueOf(pics.get(position).getGameid().getId()));
			imageloader.displayImage(pics.get(position).getUrl(), imageview, options,animateFirstListener);
			return view;
		} 
	}
	public void getData(){ 
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				try {
					String data = CommonDao.getDataFromServer(url+page+"&searchText="+searchtext); 
					list = CommonDao.StringToJson(data);
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
				} 
				adapter = new GameListAdapter();
				gamelist.setAdapter(adapter); 
				if(result.size()<7||result==null){
					ishas = false;
				} 
				page = String.valueOf((Integer.valueOf(page)+1));
				isloading = false;
				super.onPostExecute(result);
		    } 
			protected void onProgressUpdate(String... values) {
				Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
				super.onProgressUpdate(values);
			}
		}.execute(); 
	}
}
