package com.navigation.menufragment;

import com.navigation.FragmentAdapter;
import com.lt.navigation.speeddriftracing.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener{
	
	public static final int TAB_First = 0;
	public static final int TAB_Second = 1;
	private ViewPager Mainviewpager;
	private RadioButton tab_first,tab_second;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.homefragment, null);
		//初始化选卡
		Mainviewpager=(ViewPager)view.findViewById(R.id.viewpager);
		tab_first=(RadioButton)view.findViewById(R.id.first);
		tab_second=(RadioButton)view.findViewById(R.id.second);
		tab_first.setOnClickListener(this);
		tab_second.setOnClickListener(this);
		FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
		Mainviewpager.setAdapter(adapter);
		
		//设置选卡ID
		Mainviewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_First:
					tab_first.setChecked(true);
					break;
				case TAB_Second:
					tab_second.setChecked(true);
					break;
				default:
					break;
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.first:
			Mainviewpager.setCurrentItem(TAB_First);
			break;
		case R.id.second:
			Mainviewpager.setCurrentItem(TAB_Second);
			break;
		default:
			break;
		}		
	}
	
}
