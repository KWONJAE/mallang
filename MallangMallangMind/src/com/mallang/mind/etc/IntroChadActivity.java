package com.mallang.mind.etc;

import com.mallang.mind.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class IntroChadActivity extends Activity{
	private ViewPager mPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorialmain);
		
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
	}
	private View.OnClickListener mPagerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(R.id.introduce_book==((Button)v).getId()) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://chademeng.com/")));
				return;
			}
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://book.daum.net/detail/book.do?bookid=KOR9788952765208")));
		}
	};
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
			return 3;
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
    		if(position==0){
    			v = mInflater.inflate(R.layout.tutorialone, null);
    			v.findViewById(R.id.iv_one);
    		}
    		else if(position==1){
    			v = mInflater.inflate(R.layout.tutorialtwo, null);
    			v.findViewById(R.id.iv_two);
    		}else{
    			v = mInflater.inflate(R.layout.tutorialthree, null);
    			v.findViewById(R.id.introduce_book).setOnClickListener(mPagerListener);
    			v.findViewById(R.id.introduce_chad).setOnClickListener(mPagerListener);
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
