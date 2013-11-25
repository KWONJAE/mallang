package com.mallang.mind;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BasicMeditationActivity extends Activity implements OnClickListener{
	TextView title;
	Button start;
	   //매 1초 마다 증가할 정수값
    private int value = 120;
    private CountDownTimer timer;  
    

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.basicmeditationlayout);

		start = (Button)findViewById(R.id.button1);
		title = (TextView)findViewById(R.id.textView);
		
		 timer = new CountDownTimer(120 * 1000, 1000) {
	        	@Override
				public void onTick(long millisUntilFinished) {
					
		        	title.setText("Value = " + value);
		        	
		        	if( value == 0 ) 
		        	{
		        		
		        		timer.cancel();
		        		
		        		//DB에 넣기
		        		//
		        		//
		        		//
		        		//DB에 넣기
		        	}
		        	value--;
				}
	       
				@Override
				public void onFinish() {			
				}			
	        };
	       
	     
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
	    
		

	
	
	
	
	
	
}

	

	
