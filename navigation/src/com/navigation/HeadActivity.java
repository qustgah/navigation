package com.navigation; 

import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast; 

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lt.navigation.speeddriftracing.R;
import com.navigation.menufragment.HomeFragment;
import com.navigation.menufragment.SearchFragment;

public class HeadActivity extends SlidingFragmentActivity{
	public static final int TAB_First = 0;
	public static final int TAB_Second = 1;
	private CanvasTransformer mTransformer;
	private FirstScrollView s;
	 private long firstTime = 0;
	Fragment mContent;
	Fragment mmontent;   
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȫ����ʾ
		setContentView(R.layout.content_frame);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlemenu);
		initTitle();
		mContent = new HomeFragment();
		//���û����˵��򿪺����ͼ��
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		initSlidingMenu();
		initAnimation();
	}
	/**
	 * �����������¼�
	 * @param view
	 */
	public void rlclick(View view){
		TextView tv = (TextView) view.findViewById(R.id.cid);
		final String id = (String) tv.getText();
		Intent intent = new Intent();
		intent.setClass(HeadActivity.this, CategoryDetailActivity.class);
		intent.putExtra("cateid", Long.valueOf(id));
		startActivity(intent); 
	}
	/**
	 * ����title
	 */
	protected void initTitle() {
		final RelativeLayout rll = (RelativeLayout) findViewById(R.id.titlemenu);
		rll.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				rll.setFocusable(false);
				rll.setFocusableInTouchMode(true);
				rll.requestFocus();
				return false;
			}
		});
	}
	/**
	 * ���û����˵�
	 */	
	protected void initSlidingMenu(){
		
		FragmentTransaction mFragementTransaction = getSupportFragmentManager().beginTransaction();
		Fragment mFrag = new MenuFragment();//�˵���Fragment
		mFragementTransaction.replace(R.id.menu_frame, mFrag);
		mFragementTransaction.commit();
		
		//���õ��򿪻����˵�ʱ��ActionBar���ܹ�������һ�𻬶�
		setSlidingActionBarEnabled(false);
		//���û����˵�������ֵ��ʵ���������˵�����
		SlidingMenu sm = getSlidingMenu();
		//�����󻬲˵�
		sm.setMode(SlidingMenu.LEFT);
		//���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		//���û�����Ӱ��ͼ����Դ
		sm.setShadowDrawable(R.drawable.shadow);
		//���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//���ý��뽥��Ч����ֵ
		sm.setFadeDegree(0.35f);
		//���ô�����Ļ��ģʽ
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setBehindCanvasTransformer(mTransformer);
//		�����Ƿ��ܹ�ʹ��ActionBar������
		setSlidingActionBarEnabled(true);
//		���������Ա����
//		getActionBar().setHomeButtonEnabled(true);
//		//������ʾ�����ͼ��
//		getActionBar().setDisplayHomeAsUpEnabled(true);		
//		getActionBar().setTitle("");
	}
	/**
	 * ����
	 * @param source
	 */
	public void searchByText(View source){
		Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
		EditText edt = (EditText) findViewById(R.id.searchText);
		String searchtext = edt.getText().toString();
//		//��ȡ����
//		edt.requestFocus();
		source.setFocusable(true);
		source.setFocusableInTouchMode(true);
		source.requestFocus();
		Intent intent = new Intent(this,SearchActivity.class); 
		intent.putExtra("searchtext", searchtext);
		startActivity(intent);
	}
	public void showtoggle(View source){
		toggle();
		source.setFocusable(true);
		source.setFocusableInTouchMode(true);
		source.requestFocus();
	}
	/** 
     * ��ʼ������Ч�� 
     */ 
	protected void initAnimation() {
		mTransformer = new CanvasTransformer(){  
            @Override  
            public void transformCanvas(Canvas canvas, float percentOpen) {  
                float scale = (float) (percentOpen*0.25 + 0.75);  
                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);                
            }   
        };  	
	} 
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		FragmentManager manager=getSupportFragmentManager();
		FragmentTransaction tran=manager.beginTransaction();
		tran.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		  switch(keyCode)
	        {
	        case KeyEvent.KEYCODE_BACK:
	             long secondTime = System.currentTimeMillis(); 
	              if (secondTime - firstTime > 2000) {                                         
	                  Toast.makeText(this, "Again according to the exit��", Toast.LENGTH_SHORT).show(); 
	                  firstTime = secondTime;
	                  return true; 
	              } else{                                                   
	             System.exit(0);
	              } 
	            break;
	        }
		return super.onKeyDown(keyCode, event);
	}
}
