package com.navigation;


import com.lt.navigation.speeddriftracing.R;
import com.navigation.menufragment.CategoryFragment;
import com.navigation.menufragment.HomeFragment;
import com.navigation.menufragment.SearchFragment;
import com.navigation.menufragment.SecondFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout; 
public class MenuFragment extends Fragment implements OnClickListener{
	
	RelativeLayout menu_search,menu_home,menu_mine;//menu_local,menu_tuwen,menu_setting,menu_diy,menu_autochange,menu_source,menu_lockwallpaper,menu_oneclick,menu_history,
	               //menu_msgcenter,menu_downmanager,menu_weather,menu_cache,menu_reqimages,menu_helper,menu_feedback,menu_about;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.menufragment, container,false);
		
		menu_search=(RelativeLayout)view.findViewById(R.id.menu_search);
		menu_search.setOnClickListener(this);
		menu_home=(RelativeLayout)view.findViewById(R.id.menu_home);
		menu_home.setOnClickListener(this);
		menu_mine=(RelativeLayout)view.findViewById(R.id.menu_mine);
		menu_mine.setOnClickListener(this);
		
		System.out.println();
		return view;
	}
	@Override
	public void onClick(View arg0) {
		Fragment newContent = null;
		switch (arg0.getId()) {
		case R.id.menu_search:
			newContent = new SearchFragment();
			menu_search.setSelected(true);
			menu_home.setSelected(false);
			menu_mine.setSelected(false);
			break;
		case R.id.menu_home:
			newContent = new HomeFragment();
			menu_search.setSelected(false);
			menu_home.setSelected(true);
			menu_mine.setSelected(false);
			break;
		case R.id.menu_mine:
			newContent = new SecondFragment();
			menu_search.setSelected(false);
			menu_home.setSelected(false);
			menu_mine.setSelected(true);
			break;
		default:
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
//			switchFragment(getActivity(), newContent);
	}
	
	private void switchFragment(Fragment fragment) {
//		if (getActivity() == null)
//			return; 
//		Intent intent = new Intent(contenxt, fragment.getClass());
//		startActivity(intent);
		if (getActivity() == null)
			return;
			HeadActivity ra =  (HeadActivity) getActivity();
			ra.switchContent(fragment);
	}
}
