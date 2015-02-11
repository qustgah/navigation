package com.navigation.lib;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public abstract class ListAsGridBaseAdapter extends BaseAdapter {
  
	
	/**
	 * 点击事件
	 * @author Administrator
	 *
	 */
	private class ListItemClickListener implements OnClickListener {

		private int mPosition;

		public ListItemClickListener(int currentPos) {
			mPosition = currentPos;
		}

		@Override
		public void onClick(View v) {
			System.out.println("====================================================="+mPosition);
			onGridItemClicked (v, mPosition);
		}
	}
	/**
	 * 有几列
	 */
	private int mNumColumns;
	private Context mContext;
	/**
	 * 每个的点击事件
	 */
	private GridItemClickListener mGridItemClickListener;
	private int mBackgroundResource = -1;

	public ListAsGridBaseAdapter(Context context) {
		mContext = context;
		mNumColumns = 1;
	}

	/**
	 * 背景图片
	 * @param drawableResId
	 */
	public void setBackgroundResource(int drawableResId) {
		mBackgroundResource = drawableResId;
	}
	
	public final void setOnGridClickListener(GridItemClickListener listener) {
		mGridItemClickListener = listener;
	}

	/**
	 * 每个的点击事件
	 * @param v
	 * @param position
	 */
	private final void onGridItemClicked(View v, int position) {
		if (mGridItemClickListener != null) {
			mGridItemClickListener.onGridItemClicked(v, position, getItemId(position));
		}
	}

	public final int getNumColumns() {
		return mNumColumns;
	}

	public final void setNumColumns(int numColumns) {
		mNumColumns = numColumns;
		notifyDataSetChanged();
	}

	/**
	 * 获取个数
	 */
	@Override
	public final int getCount() {
		return (int) Math.ceil(getItemCount()*1f / getNumColumns());
	}

	
	/**
	 * item 个数
	 * @return
	 */
	public abstract int getItemCount();

	protected abstract View getItemView(int position, View view, ViewGroup parent);

	
	@Override
	/**
	 * 获取单个的view
	 */
	public final View getView(int position, View view, ViewGroup viewGroup) {
		LinearLayout layout;
		int columnWidth = 0;
		if (viewGroup !=  null) {
			columnWidth = viewGroup.getWidth()/mNumColumns;    		
		}
		else if (view != null) {
			columnWidth = view.getWidth()/mNumColumns;
		}
		if (view == null) {
			layout = createItemRow(position, viewGroup, columnWidth);            
		}
		else {
			layout = (LinearLayout) view;
			updateItemRow(position, viewGroup, layout, columnWidth);
		}
		return layout;
	}

	/**
	 * 创建新的行
	 * @param position
	 * @param viewGroup
	 * @param columnWidth
	 * @return
	 */
	private LinearLayout createItemRow(int position, ViewGroup viewGroup, int columnWidth) {		
		LinearLayout layout;
		layout = new LinearLayout(mContext);
		if (mBackgroundResource > 0) {
			layout.setBackgroundResource(mBackgroundResource);
		}
		int adjustedColumnWidth = columnWidth - (layout.getPaddingLeft() + layout.getPaddingRight())/mNumColumns;
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// Now add the sub views to it
		for (int i = 0; i < mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			// Get the new View
			View insideView;
			if (currentPos < getItemCount()) {            		
				insideView = getItemView(currentPos, null, viewGroup);         	
				insideView.setVisibility(View.VISIBLE);
				View theView = getItemView(currentPos, insideView, viewGroup);
				theView.setOnClickListener(new ListItemClickListener (currentPos));	
			}
			else {
				insideView = new View(mContext);
				insideView.setVisibility(View.INVISIBLE);
			}
			layout.addView(insideView); 
			LayoutParams params = insideView.getLayoutParams();
			params.width = adjustedColumnWidth;
			insideView.setLayoutParams(params);	
		}
		return layout;
	} 
	/**
	 * 更新行
	 * @param position
	 * @param viewGroup
	 * @param layout
	 * @param columnWidth
	 */
	private void updateItemRow(int position, ViewGroup viewGroup, LinearLayout layout, int columnWidth) {
		int realColumnWidth = columnWidth - (layout.getPaddingLeft() + layout.getPaddingRight())/mNumColumns;
		for (int i=0; i<mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			View insideView = layout.getChildAt(i);
			// If there are less views than objects. add a view here
			if (insideView == null) {
				insideView = new View(mContext);
				layout.addView(insideView);
			}
			// Set the width of this column
			LayoutParams params = insideView.getLayoutParams();
			params.width = realColumnWidth;
			insideView.setLayoutParams(params);

			if (currentPos < getItemCount()) {
				insideView.setVisibility(View.VISIBLE);
				// Populate the view
				View theView = getItemView(currentPos, insideView, viewGroup);
				theView.setOnClickListener(new ListItemClickListener (currentPos));				
				if (!theView.equals(insideView)) {
					// DO NOT CHANGE THE VIEWS
					System.out.println("-------------------------------------------------------");
				}
			}
			else {
				insideView.setVisibility(View.INVISIBLE);
			}
		}
	}
	
}