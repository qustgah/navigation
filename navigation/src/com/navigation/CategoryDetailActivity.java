package com.navigation;

import android.app.Activity;
import android.os.Bundle; 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity; 
import com.lt.navigation.speeddriftracing.R;
import com.navigation.menufragment.CategoryDetailFragment;
import com.navigation.menufragment.CategoryFragment;

 
public class CategoryDetailActivity extends FragmentActivity{
	Fragment mContent;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame); 
		mContent = new CategoryDetailFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
	}  
}
