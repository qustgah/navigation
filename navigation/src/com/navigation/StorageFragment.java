package com.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.lt.navigation.speeddriftracing.R;

public class StorageFragment extends Fragment{
	private ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.storage, container);
		return super.onCreateView(inflater, container, savedInstanceState);
		
	}

}
