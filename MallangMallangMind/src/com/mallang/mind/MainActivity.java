package com.mallang.mind;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;



public class MainActivity extends BaseActivity implements OnClickListener{
	
	private Fragment mContent;
	
	TextView title;
	Button start;
	   //매 1초 마다 증가할 정수값
    private int value = 120;
    private CountDownTimer timer;  
    
	public MainActivity() {
		super(R.id.textView1);
	}

		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setSlidingActionBarEnabled(true);
	        // set the content view
	        setContentView(R.layout.activity_main);
	        
	        
	        
	     // set the Above View
			if (savedInstanceState != null)
				mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
			if (mContent == null)
				mContent = new MyInfoFragment();	
	        start = (Button)findViewById(R.id.button1);
			title = (TextView)findViewById(R.id.textView1);
			
			 
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
		public void onClick(View v) { //시작 버튼
			// TODO Auto-generated method stub
	        switch (v.getId()) {

			
			case R.id.button1:
				value = 120;
				timer.start();
				break;

			}
			
			
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