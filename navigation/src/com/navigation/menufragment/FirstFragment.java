package com.navigation.menufragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.navigation.ChildViewPager;
import com.navigation.OnSingleTouchListener;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.bean.LocalImageAndDescribe;
import com.navigation.bean.Picture;
import com.navigation.common.imageLoader.AnimateFirstDisplayListener;
import com.navigation.dao.CommonDao;
import com.navigation.dao.GameDao;
import com.navigation.lib.GridItemClickListener;
import com.navigation.lib.ListAsGridBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

public class FirstFragment extends BaseFragment{
	private ListAsGridExampleAdapter mAdapter; // adapter
	private ChildViewPager firstviewpager = null;
	private boolean isloading = false;//判断是否加载中
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private ImageView mPage5;
	private ArrayList<Picture> pics ;//
	int currIndex = 0;
	private JazzyListView listView; 
	private boolean ishas = true; //是否还有数据
	private String page = "1"; //获取第几页的信息
	String url ;
	String spurl;
	private AdView mAdView;
	private ArrayList<View> views;
	private ArrayList<Picture> pics_sp;
	View view ;
	String version;
	 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
	public DisplayImageOptions options;
	ImageLoadingListener animateFirstListener;
	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.firstfragment, null);
		url = getResources().getString(R.string.ReconUrl)+"?page=";
		spurl = getResources().getString(R.string.ReconUrl_sp)+"?page=1";
		version = getResources().getString(R.string.version);
		listView = (JazzyListView) view.findViewById(R.id.listView);
//		listView.setTransitionEffect(JazzyHelper.GROW);
		/**
		 * 广告
		 */
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.firstfragmentad);
		mAdView = new AdView(getActivity());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		/*在listview的头部加入内容 */
		View header = View.inflate(getActivity(), R.layout.header, null);
		firstviewpager = (ChildViewPager) header.findViewById(R.id.firstfragemt_viewpager);
		listView.addHeaderView(header);
		pics_sp = new ArrayList<Picture>();
		views = new ArrayList<View>();
		animateFirstListener = new AnimateFirstDisplayListener();
		options = new DisplayImageOptions.Builder()  
	       .showStubImage(R.drawable.stub_image)          // 设置图片下载期间显示的图片  
	       .showImageForEmptyUri(R.drawable.stub_image)  // 设置图片Uri为空或是错误的时候显示的图片  
	       .showImageOnFail(R.drawable.stub_image)       // 设置图片加载或解码过程中发生错误显示的图片      
	       .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
	       .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
	       .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片  
	       .build();                                   // 创建配置过得DisplayImageOption对象  
		getData();
		firstviewpager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		}); 
		firstviewpager.onSingleTouchListener = new OnSingleTouchListener(){
			@Override
			public void onSingleTouch() {
				System.out.println(currIndex);
				View vp = views.get(currIndex);
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailActivity.class);
				ImageView image = (ImageView) vp.findViewById(R.id.itemimg_g);
				int gameid = (Integer) image.getTag();
				intent.putExtra("gameid",Long.valueOf(gameid));
				startActivity(intent); 
			}
		};
		//加载滑动推荐图片
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				try {
					String data = CommonDao.getDataFromServer(spurl+"&belong="+version); 
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
					pics_sp = GameDao.getPictures(result);
				} catch (Exception e) { 
					e.printStackTrace();
				}
				mPage0 = (ImageView) view.findViewById(R.id.page0);
				mPage1 = (ImageView) view.findViewById(R.id.page1);
				mPage2 = (ImageView) view.findViewById(R.id.page2);
				mPage3 = (ImageView) view.findViewById(R.id.page3);
				mPage4 = (ImageView) view.findViewById(R.id.page4);
				mPage5 = (ImageView) view.findViewById(R.id.page5);

				// 填充ViewPager的数据适配器
				final PagerAdapter mPagerAdapter = new PagerAdapter() {
					@Override
					public boolean isViewFromObject(View arg0, Object arg1) {
						return arg0 == arg1;
					}
					@Override
					public int getCount() {
						return pics_sp.size();
					}

					@Override
					public void destroyItem(View container, int position, Object object) {
						((ViewPager) container).removeView((View)object);
					}

					@Override
					public Object instantiateItem(View container, int position) {
						View view = View.inflate(getActivity(), R.layout.first_viewpager1, null); 
						ImageView imageView = (ImageView) view.findViewById(R.id.itemimg_g);
						imageView.setTag(pics_sp.get(position).getGameid().getId());
						imageloader.displayImage(pics_sp.get(position).getUrl(), imageView, options,animateFirstListener);
						((ViewPager) container).addView(view);
						if (views.size() < 6) { 
							views.add(view);
						} 
						return view;
					}
				};
				firstviewpager.setAdapter(mPagerAdapter);  
				firstviewpager.setOnPageChangeListener(new OnPageChangeListener() {
					@Override
					public void onPageSelected(int arg0) {  
						switch (arg0) {
						case 0:
							mPage0.setImageDrawable(getResources().getDrawable(
									R.drawable.page_now));
							mPage1.setImageDrawable(getResources().getDrawable(
									R.drawable.page));
							break;
						case 1:
							mPage1.setImageDrawable(getResources().getDrawable(
									R.drawable.page_now));
							mPage0.setImageDrawable(getResources().getDrawable(
									R.drawable.page));
							mPage2.setImageDrawable(getResources().getDrawable(
									R.drawable.page));
							break;
						case 2:
							mPage2.setImageDrawable(getResources().getDrawable(
									R.drawable.page_now));
							mPage1.setImageDrawable(getResources().getDrawable(
									R.drawable.page));
							mPage3.setImageDrawable(getResources().getDrawable(
									R.drawable.page));
							break;
						case 3:
							mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
							mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
							mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
							break;
						case 4:
							mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
							mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
							mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
							break;
						case 5:
							mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
							mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
							break;
							
						}
						currIndex = arg0;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}
					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				}); 
				isloading = false;
				super.onPostExecute(result);
			} 
			protected void onProgressUpdate(String... values) {
				Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
				super.onProgressUpdate(values);
			}
		}.execute();    
		// 滑到底部加载新的图片
		listView.setOnScrollListener(new OnScrollListener() {
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
								String data = CommonDao.getDataFromServer(url+page+"&belong="+version);
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
							List<Map<String, Object>> picurls = new ArrayList<Map<String, Object>>();
							picurls = result;
							List<Picture> mylist = new ArrayList<Picture>();
							try {
								mylist = GameDao.getPictures(result);
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}
							pics.addAll(mylist); 
							mAdapter.notifyDataSetChanged();
							page = String.valueOf((Integer.valueOf(page) + 1));
							isloading = false;
							if (result.size() < 8 || result == null) {
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
				
			}
		});
		return view;
	}
	public void getData(){
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				try {
					String data = CommonDao.getDataFromServer(url+page+"&belong="+version); 
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
				pics = new ArrayList<Picture>();
				try {
					pics = GameDao.getPictures(result);
				} catch (Exception e) { 
					e.printStackTrace();
				} 

				mAdapter = new ListAsGridExampleAdapter(getActivity()); 
				mAdapter.setNumColumns(3);//控制几列
				mAdapter.setOnGridClickListener(new GridItemClickListener() {
					@Override
					public void onGridItemClicked(View v, int position, long itemId) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), DetailActivity.class);
						intent.putExtra("gameid", itemId);
						startActivity(intent);
					}
				});
				
				page = String.valueOf((Integer.valueOf(page)+1));
				if(result.size()<8||result==null){
					ishas = false;
				}  
				isloading = false;
				listView.setAdapter(mAdapter);
				super.onPostExecute(result);
			} 
			protected void onProgressUpdate(String... values) {
				Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
				super.onProgressUpdate(values);
			}
		}.execute(); 
	}
	
	class ListAsGridExampleAdapter extends ListAsGridBaseAdapter {
		ArrayList<LocalImageAndDescribe> mm=new ArrayList<LocalImageAndDescribe>();
		
		private LayoutInflater mInflater;
		Context context;
		public ListAsGridExampleAdapter(Context context ) {
			super(context);
			this.context = context;
			mInflater = LayoutInflater.from(context);
		} 
		@Override
		public long getItemId(int position) {
			return pics.get(position).getGameid().getId();
		} 

		@Override
		public int getItemCount() {
			return pics.size();
		}
		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		/* (non-Javadoc)
		 * @see com.navigation.lib.ListAsGridBaseAdapter#getItemView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		protected View getItemView(int position, View convertview, ViewGroup parent) {
			View myview = null;  
			TextView tv = null;
			ImageView iv = null;
			ImageView div=null;
			if (convertview == null) {
				myview  = mInflater.inflate(R.layout.simple_list_item, null);
				tv = (TextView) myview.findViewById(R.id.text);   
				 iv=(ImageView) myview.findViewById(R.id.itemimg);
//				 div=(ImageView)myview.findViewById(R.id.imgdiv);
			}else{
				myview = convertview;
				tv = (TextView) myview.findViewById(R.id.text);   
				iv=(ImageView) myview.findViewById(R.id.itemimg);
//				 div=(ImageView)myview.findViewById(R.id.imgdiv);
			}
			imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
//			 div.setImageDrawable(getResources().getDrawable(R.drawable.div));
			tv.setText(pics.get(position).getGameid().getName());
			imageloader.displayImage(pics.get(position).getUrl(),iv,options,animateFirstListener);
			return myview;
		}
		@Override
		public Object getItem(int position) {
			return pics.get(position);
		} 
	}
}
