package com.mallang.mind;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class TutorialPageFragment extends Fragment{
	private ViewPager mPager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.tutorialmain, container, false);
			
			mPager = (ViewPager)v.findViewById(R.id.pager);
			mPager.setId("mPager".hashCode());
			mPager.setAdapter(new TutorialPagerAdapter(getFragmentManager()));
			

			mPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int arg0) { }

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) { }

				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:
						((MainActivity) getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
						break;
					default:
						((MainActivity) getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
						break;
					}
				}


			});
			
			mPager.setCurrentItem(0);
			return v;
			
	
		
	}
	public class TutorialPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;

		
		
		public TutorialPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			
				mFragments.add(0, new TutorialOneFragment());
				mFragments.add(1, new TutorialTwoFragment());
				mFragments.add(2, new TutorialThreeFragment());
		
		}
		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

	}


}




	

