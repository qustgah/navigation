package com.navigation;

import com.lt.navigation.speeddriftracing.R;
import com.navigation.menufragment.SearchFragment;
import android.os.Bundle;

public class SearchActivity extends BaseActivity{
	
	public String searchtext ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);
		initTitle();
		mContent = new SearchFragment(); 
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		initSlidingMenu();
		initAnimation();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
}
