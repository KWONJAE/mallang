package com.mallang.mind;

import com.mallang.mind.db.DbOpenHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BasicMeditationFragment extends Fragment implements OnClickListener {
	private DbOpenHelper mDbOpenHelper;
	TextView title;
	Button start;
	   //�� 1�� ���� ������ ������
    private int value = 120;
    private boolean isFinished;
    private CountDownTimer timer;  
    private SharedPreferences pref;
    private View v;
    @SuppressWarnings("static-access")
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	isFinished=false;
		v = inflater.inflate(R.layout.basic_medi_layout, container, false);
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
	        	}
	        	value--;
			}
       
			@Override
			public void onFinish() {
				isFinished=true;
				RelativeLayout basicBack = (RelativeLayout) v.findViewById(R.id.basic_medi_back);
				basicBack.setBackgroundResource(R.drawable.medi_end_back);
				start.setBackgroundResource(R.drawable.home);
				Vibrator vibe = (Vibrator) ((MainActivity) getActivity()).getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(500); // 0.5��
				//save log
				title.setText("Value = 0");
				mDbOpenHelper.open();	                
                String userID = pref.getString("userID", "");
                mDbOpenHelper.insertLog(userID, 1, 2);
                mDbOpenHelper.close();
			}			
	        };
	       
	        return v;
	}

		
		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) { //���� ��ư
			// TODO Auto-generated method stub
	        if (v.getId()==R.id.startbutton) {
	        	if(isFinished) {
	        		((MainActivity)getActivity()).switchContent(new MyInfoFragment());
	        	}
				value = 120;
				timer.start();
				start.setBackgroundResource(R.drawable.finish);
				
			}
		}
	
}