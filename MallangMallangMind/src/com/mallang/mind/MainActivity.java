package com.mallang.mind;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity{
	
		private Fragment mContent;
	
	
	    
		public MainActivity() {
			super(R.string.notitlename);
		}

		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setSlidingActionBarEnabled(true);
	        
	        // set the content view
	        setContentView(R.layout.activity_main);
	        //set app is not go to sleep
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        
	        
	        // set the Above View
			if (savedInstanceState != null)
				mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
			if (mContent == null)
				mContent = new MyInfoFragment();	
	       
		
			
			 
		    // set the Above View
			setContentView(R.layout.content_frame);
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, mContent)
			.commit();
			
			// set the Behind View
			setBehindContentView(R.layout.menu_frame);
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame, new ListMenuFragment())
			.commit();
			
			// customize the SlidingMenu
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        
	    }
	
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
		}
		public void switchContent(Fragment fragment) {
			mContent = fragment;
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, fragment)
			.commit();
			getSlidingMenu().showContent();
		}
	}