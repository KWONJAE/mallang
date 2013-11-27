package com.mallang.mind;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BasicMeditationFragment extends Fragment implements OnClickListener {
	
	TextView title;
	Button start;
	   //매 1초 마다 증가할 정수값
    private int value = 120;
    private CountDownTimer timer;  
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_main, container, false);

		start = (Button) v.findViewById(R.id.button1);
		title = (TextView)v.findViewById(R.id.textView1);
		start.setOnClickListener(this);
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
	       
	        return v;
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