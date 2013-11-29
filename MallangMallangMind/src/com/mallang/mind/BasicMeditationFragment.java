package com.mallang.mind;

import com.mallang.mind.db.DbOpenHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BasicMeditationFragment extends Fragment implements OnClickListener {
	private DbOpenHelper mDbOpenHelper;
	TextView title;
	Button start;
	   //매 1초 마다 증가할 정수값
    private int value = 120;
    private CountDownTimer timer;  
    private SharedPreferences pref;
    @SuppressWarnings("static-access")
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.basicmeditationlayout, container, false);
		mDbOpenHelper = new DbOpenHelper(this.getActivity().getBaseContext());
		pref = this.getActivity().getSharedPreferences("pref", this.getActivity().MODE_PRIVATE);
		start = (Button) v.findViewById(R.id.startbutton);
		title = (TextView)v.findViewById(R.id.timetext);
		start.setOnClickListener(this);
		 timer = new CountDownTimer(120 * 1000, 1000) {
	        	@Override
				public void onTick(long millisUntilFinished) {
					
		        	title.setText("Value = " + value);
		        	
		        	if( value == 0 ) 
		        	{
		        		
		        		timer.cancel();
		        		//save log
		                mDbOpenHelper.open();	                
		                String userID = pref.getString("userID", "");
		                mDbOpenHelper.insertLog(userID, 1, 2);
		                mDbOpenHelper.close();
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

			
			case R.id.startbutton:
				value = 120;
				timer.start();
				break;

			}
			
			
		}
	
}