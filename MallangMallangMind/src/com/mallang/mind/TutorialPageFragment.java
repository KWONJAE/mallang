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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.tutorialmain, container, false);
			
			ViewPager vp = (ViewPager)v.findViewById(R.id.pager);
			vp.setId("VP".hashCode());
			vp.setAdapter(new TutorialPagerAdapter(getFragmentManager()));
			

			vp.setOnPageChangeListener(new OnPageChangeListener() {
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
			
			vp.setCurrentItem(0);
			return v;
			
	
		
	}
	public class TutorialPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;

		
		
		public TutorialPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			
				mFragments.add(new TutorialOneFragment());
				mFragments.add(new TutorialTwoFragment());
				mFragments.add(new TutorialThreeFragment());
		
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




	

