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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TwoMeditationFragment extends Fragment {
	
	private DbOpenHelper mDbOpenHelper;
	private TextView text1;
	private CountDownTimer timer1;
	private int turnCounter=0;
	private Button btnStart, btnEnd;
	private boolean isRun, isFinished;
	private SharedPreferences pref;
	private View v;
	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			v = inflater.inflate(R.layout.two_medi_layout, container, false);
		
		
			turnCounter=0;
			text1 = (TextView) v.findViewById(R.id.timer_main_text);
			btnStart = (Button) v.findViewById(R.id.timer_main_start);
			btnEnd = (Button) v.findViewById(R.id.timer_main_end);
			
			mDbOpenHelper = new DbOpenHelper(this.getActivity().getBaseContext());
			pref = this.getActivity().getSharedPreferences("pref", this.getActivity().MODE_PRIVATE);
			isRun=false;
			isFinished=false;
			//text2.setText("3min for one person 마지막 3분은 서로 이야기");
			timer1 = new CountDownTimer(180*1000, 1000) {
				@Override
				public void onFinish() {
					if(turnCounter>3) {
						RelativeLayout basicBack = (RelativeLayout) v.findViewById(R.id.basic_medi_back);
						basicBack.setBackgroundResource(R.drawable.medi_end_back);
						btnEnd.setBackgroundResource(R.drawable.home);
						//save log
						mDbOpenHelper.open();
						String userID = pref.getString("userID", "");
		                mDbOpenHelper.insertLog(userID, 2, turnCounter*3);
		                mDbOpenHelper.close();
		                isFinished=true;
		                timer1.cancel();
						text1.setText("");
		                turnCounter=0;
						isRun=false;
		                return;
					}
					isRun=false;
					if(turnCounter%2==0) {
						text1.setText("Now Player 1, please start");
						btnStart.setBackgroundResource(R.drawable.start);
						return;
					}
					text1.setText("Now Player 2, please start");
					btnStart.setBackgroundResource(R.drawable.start);
				}

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					long time = millisUntilFinished/1000;
					int seconds = (int)(time%60);
					int minutes = (int)(time%3600)/60;
					text1.setText(minutes+" : "+seconds);
				}
			};
			btnStart.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(isRun) {
						return;
					}
					turnCounter++;
					timer1.start();
					btnStart.setBackgroundResource(R.drawable.talk);
					isRun=true;
				}
				
			});
			btnEnd.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(isFinished) {
						((MainActivity)getActivity()).switchContent(new MyInfoFragment());
					}
					timer1.cancel();
					text1.setText("It is canceled");
					if(turnCounter>0) {
						Vibrator vibe = (Vibrator) ((MainActivity) getActivity()).getSystemService(Context.VIBRATOR_SERVICE);
		                vibe.vibrate(500); // 0.5초
						mDbOpenHelper.open();
						String userID = pref.getString("userID", "");
		                mDbOpenHelper.insertLog(userID, 2, turnCounter*3);
		                mDbOpenHelper.close();
					}
					turnCounter=0;
					isRun=false;
					btnStart.setBackgroundResource(R.drawable.start);
				}
				
			});
			return v;
		}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
	}
	
	
	
}