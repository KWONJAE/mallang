package com.mallang.mind.etc;

import com.mallang.mind.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class IntroChadActivity extends Activity{
	private ViewPager mPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etc_main);
		
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
	}
	/**
	 * PagerAdapter 
	 */
	private class PagerAdapterClass extends PagerAdapter{
		
		private LayoutInflater mInflater;

		public PagerAdapterClass(Context c){
			super();
			mInflater = LayoutInflater.from(c);
		}
		
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
    		if(position==0){
    			v = mInflater.inflate(R.layout.etc_one, null);
    			v.findViewById(R.id.iv_one);
    		}
    		else {
    			v = mInflater.inflate(R.layout.etc_two, null);
    			v.findViewById(R.id.iv_two);
    		}
    		
    		((ViewPager)pager).addView(v, 0);
    		
    		return v; 
		}

		@Override
		public void destroyItem(View pager, int position, Object view) {	
			((ViewPager)pager).removeView((View)view);
		}
		
		@Override
		public boolean isViewFromObject(View pager, Object obj) {
			return pager == obj; 
		}

		@Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
		@Override public Parcelable saveState() { return null; }
		@Override public void startUpdate(View arg0) {}
		@Override public void finishUpdate(View arg0) {}
	}
}
