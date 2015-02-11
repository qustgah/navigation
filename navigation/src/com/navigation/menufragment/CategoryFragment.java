package com.navigation.menufragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.w3c.dom.Text;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.navigation.CategoryDetailActivity;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.bean.CateAndGames;
import com.navigation.bean.Category;
import com.navigation.bean.Picture;
import com.navigation.dao.CategoryDao;
import com.navigation.dao.CommonDao;
import com.navigation.dao.GameDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class CategoryFragment extends BaseFragment {
	private AdView mAdView;
	LayoutInflater inflater = null;
	ListView listview = null;
	GridView gridview = null;
	CateAdapter cateAdapter = null;
	Recon_CateAdapter recon = null;	 
	private boolean isloading = false;//�ж��Ƿ������
	private boolean isScrolling = false;// �Ƿ����ڹ���
//	private String caturl;
	private String reconurl;
	private String page = "0";
	private boolean ishas = true; // �Ƿ�������
	private String cid ;
	public DisplayImageOptions options;
	public ArrayList<List<Picture>>  pll ;
	
	public ArrayList<CateAndGames> cateAndGameslist = new ArrayList<CateAndGames>();
	 	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.categoryfragment, null);
		/**
		 * ���
		 */
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.catergoryfragmentad);
		mAdView = new AdView(getActivity());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		listview = (ListView) view.findViewById(R.id.categories);
//		caturl = getResources().getString(R.string.AllCategroy);
		pll = new ArrayList<List<Picture>>();
		
		reconurl = getResources().getString(R.string.ReconGameByCate_sp)+"?page=0&cid=";
		
		
		options = new DisplayImageOptions.Builder()  
	       .showStubImage(R.drawable.stub_image)          // ����ͼƬ�����ڼ���ʾ��ͼƬ  
	       .showImageForEmptyUri(R.drawable.stub_image)  // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
	       .showImageOnFail(R.drawable.stub_image)       // ����ͼƬ���ػ��������з���������ʾ��ͼƬ      
	       .cacheInMemory(true)                        // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
	       .cacheOnDisc(true)                          // �������ص�ͼƬ�Ƿ񻺴���SD����  
	       .displayer(new RoundedBitmapDisplayer(20))  // ���ó�Բ��ͼƬ  
	       .build();                                   // �������ù���DisplayImageOption����  
		
		
		getData(); 
		return view;
	} 
	class CateAdapter extends BaseAdapter{

		ArrayList<CateAndGames> categames = new ArrayList<CateAndGames>();
		public CateAdapter(ArrayList<CateAndGames> cateAndGameslist) {
			this.categames = cateAndGameslist;
		}
		@Override
		public int getCount() {
			return categames.size();
		}
		@Override
		public Object getItem(int position) {
			return categames.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) { 
			View view;  
			TextView textview = null;
			RelativeLayout rl = null;
			TextView tv1 = null;
			final int a = position;
			if (convertView == null) {
				view = inflater.inflate(R.layout.category_detail , null);
				textview = (TextView) view.findViewById(R.id.gameName);
				tv1 = (TextView) view.findViewById(R.id.cid);
				gridview = (GridView) view.findViewById(R.id.categorylist);
			}else{
				view = convertView;
				tv1 = (TextView) view.findViewById(R.id.cid);
				textview = (TextView) view.findViewById(R.id.gameName);
				gridview = (GridView) view.findViewById(R.id.categorylist);
			}
			
			tv1.setText(String.valueOf(categames.get(position).getCategory().getId()));
			textview.setText(categames.get(position).getCategory().getName());
			cid = String.valueOf(categames.get(position).getCategory().getId());
			gridview.setAdapter(new Recon_CateAdapter(categames.get(position).getGames()));
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) { 
					Intent intent = new Intent();
					intent.setClass(getActivity(), DetailActivity.class);
					intent.putExtra("gameid",id);
					startActivity(intent);
				} 
			});
			return view; 
		}
	}
	class Recon_CateAdapter extends BaseAdapter{
		ArrayList<Picture> pics ;
		public Recon_CateAdapter(List<Picture> list) {
			 this.pics = (ArrayList<Picture>) list; 
		}
		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return pics.get(position);
		}
		@Override
		public long getItemId(int position) {
			return pics.get(position).getGameid().getId();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView ;
			ImageView imageview1 = null; 
			TextView textView1 = null; 
			imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
			if (convertView == null) {
				view = inflater.inflate(R.layout.category_cdetail , null);
				view.setId(pics.get(position).getGameid().getId());
			    imageview1 = (ImageView) view.findViewById(R.id.game1_pic);
			    textView1 = (TextView) view.findViewById(R.id.game1_txt);
			} else{
				imageview1 = (ImageView) view.findViewById(R.id.game1_pic);
				textView1 = (TextView) view.findViewById(R.id.game1_txt);
			}
			imageloader.displayImage(pics.get(position).getUrl(), imageview1,options);
			textView1.setText(pics.get(position).getGameid().getName());
			return view; 
		}
	}
	
	
	
	public void getData(){
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
				try {
					String data = CommonDao.getDataFromServer(reconurl); 
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
					cateAndGameslist = CategoryDao.getCateAndGames(result);
				} catch (JSONException e) { 
					System.out.println(e.getMessage().toString());
				}  
				cateAdapter = new CateAdapter(cateAndGameslist);
				listview.setAdapter(cateAdapter);
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
