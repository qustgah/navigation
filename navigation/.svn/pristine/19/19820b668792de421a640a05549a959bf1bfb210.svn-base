package com.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.navigation.menufragment.CategoryFragment;
import com.navigation.menufragment.FirstFragment;
import com.navigation.menufragment.SecondFragment;
/**
 * pagerView 实现
 *
 */
public class FragmentAdapter extends FragmentPagerAdapter {

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	/**
	 * 两个Fragement
	 */
	public final static int TAB_COUNT = 2;

	@Override
	public Fragment getItem(int id) {
		switch (id) { 
		case HeadActivity.TAB_First://推荐首页
			FirstFragment firstfragment = new FirstFragment();
			return firstfragment;
		case HeadActivity.TAB_Second://所有游戏页
			CategoryFragment secondfragment = new CategoryFragment();
			return secondfragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
