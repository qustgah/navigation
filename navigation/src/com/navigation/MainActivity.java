package com.navigation; 

import com.lt.navigation.speeddriftracing.R;
import com.navigation.dao.CommonDao;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ÉèÖÃÈ«ÆÁÏÔÊ¾
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.appstart);
		boolean isnet = CommonDao.checkNetwork(this);
		if(!isnet){
			CommonDao.netConnectDialog(this);
		}else{
			new CountDownTimer(2000,1000){
				@Override
				public void onTick(long millisUntilFinished) {
				}
				@Override
				public void onFinish() {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, HeadActivity.class);
					startActivity(intent);
					int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
					if(VERSION >= 5){
						overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
					}
					finish();
				}
			}.start();
		}
	} 
}
