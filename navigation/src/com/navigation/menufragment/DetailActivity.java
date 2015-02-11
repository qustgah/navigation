package com.navigation.menufragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.bean.Picture;
import com.navigation.dao.CommonDao;
import com.navigation.dao.GameDao;
import com.navigation.db.DBhelper;
import com.navigation.lib.GridItemClickListener;
import com.navigation.menufragment.FirstFragment.ListAsGridExampleAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DetailActivity extends Activity {
	
	public ImageLoader imageloader = ImageLoader.getInstance();
	public DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.stub_image) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.stub_image) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.stub_image) // 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
			.build();
	public DisplayImageOptions options2 = new DisplayImageOptions.Builder()
	.showStubImage(R.drawable.stub_image) // 设置图片下载期间显示的图片
	.showImageForEmptyUri(R.drawable.stub_image) // 设置图片Uri为空或是错误的时候显示的图片
	.showImageOnFail(R.drawable.stub_image) // 设置图片加载或解码过程中发生错误显示的图片
	.displayer(new RoundedBitmapDisplayer(20))
	.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
	.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
	.build();
	private ImageView logoimg;
	public String url = "";
	public String classify_url = "";
	private ExpandableListView listview;
	private Button bt;
	public Resources res;
	private Button stobt;
	public ArrayList<String> list;
	public int gameid;
	private ArrayList<Picture> pics = new ArrayList<Picture>();
	private boolean isloading = true;
	private String gameurl; 
	private LinearLayout ll;
	private LinearLayout game_imgs;
	private TextView size;
	private TextView description;
	private TextView appaddress;
	private TextView appname;
	private AdView mAdView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView(R.layout.detail);
		/**
		 * 广告
		 */
		LinearLayout layout = (LinearLayout)findViewById(R.id.detailad);
		mAdView = new AdView(getApplicationContext());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		ll = (LinearLayout) findViewById(R.id.appinfos);
		game_imgs = (LinearLayout) findViewById(R.id.game_imgs);
		logoimg = (ImageView) findViewById(R.id.gameslogo);
		size = (TextView) findViewById(R.id.size);
		description = (TextView) findViewById(R.id.description);
//		appaddress = (TextView) findViewById(R.id.appaddress);
		appname = (TextView) findViewById(R.id.appname);
		
		bt = (Button) findViewById(R.id.download);
		gameid = (int) getIntent().getLongExtra("gameid", 0);
		gameurl = getResources().getString(R.string.PictureForGame)+"?gameid="+gameid ;  
		new AsyncTask<Void, String, List<Map<String, Object>>>() {
			protected List<Map<String, Object>> doInBackground(Void... params) {
				List<Map<String, Object>> list = null;
				try {
					String data = CommonDao.getDataFromServer(gameurl); 
					list = CommonDao.StringToJson(data);
				} catch (Exception e) {
					System.out.println(e.getMessage().toString());
				}
				return list;
			}
			protected void onPreExecute() {
				isloading = true;
				Toast.makeText(DetailActivity.this, "loading...", 3).show();
				super.onPreExecute();
			}
			protected void onPostExecute(List<Map<String, Object>> result) {
				try {
					pics = GameDao.getPictures(result);
				} catch (Exception e){ 
					e.printStackTrace();
				} 
				String downloadurl = null;
				imageloader.init(ImageLoaderConfiguration.createDefault(DetailActivity.this));
				for(Picture pic : pics){
					if(pic.getType().equals("0")){
						imageloader.displayImage(pic.getUrl(), logoimg, options2);
					}else{ 
						 LinearLayout.LayoutParams imagelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
						 ImageView image = new ImageView(getApplicationContext());
						 ImageView image2 = new ImageView(getApplicationContext());
						 imageloader.displayImage(pic.getUrl(), image,options);
						 image.setLayoutParams(new LayoutParams(400, 200));
						 image.setClickable(true);
						 image.setScaleType(ScaleType.FIT_XY);
						 image2.setImageResource(R.drawable.dv);
						 game_imgs.addView(image);
						 game_imgs.addView(image2);
						 image.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
							}
						});
					}
					size.setText(pic.getGameid().getSize());
					description.setText(pic.getGameid().getDescription());
//					appaddress.setText(pic.getGameid().getStore().getName());
					appname.setText(pic.getGameid().getName());
					downloadurl = pic.getGameid().getLoadurl();
				}
				final String  path = downloadurl;
				bt.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id="+path));
						startActivity(intent);
					}
				});
				isloading = false;
				super.onPostExecute(result);
			}
			protected void onProgressUpdate(String... values) {
				Toast.makeText(DetailActivity.this, values[0], Toast.LENGTH_SHORT).show();
				super.onProgressUpdate(values);
			}  
		}.execute();  
	}
}
