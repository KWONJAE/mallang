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


public class TwoMeditationFragment extends Fragment {
	
	
	private TextView text1;
	private TextView text2;
	private CountDownTimer timer1;
	private int turnCounter=0;
	private Button btnStart, btnEnd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timer_main, container, false);
		
		
			turnCounter=0;
			text1 = (TextView) v.findViewById(R.id.timer_main_text);
			//text2 = (TextView) getActivity().findViewById(R.id.textView1);
			btnStart = (Button) v.findViewById(R.id.timer_main_start);
			btnEnd = (Button) v.findViewById(R.id.timer_main_end);
			
			
			//text2.setText("3min for one person ������ 3���� ���� �̾߱�");
			timer1 = new CountDownTimer(180*1000, 1000) {
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					if(turnCounter%2==0) {
						text1.setText("Now Player 1, please start");
						btnStart.setText("Player 1 Start");
						return;
					}
					text1.setText("Now Player 2, please start");
					btnStart.setText("Player 2 Start");
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
					// TODO Auto-generated method stub
					turnCounter++;
					timer1.start();
					btnStart.setText("Please Talk...");
				}
				
			});
			btnEnd.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					timer1.cancel();
					text1.setText("It is canceled");
					turnCounter=0;
					btnStart.setText("Start Timer");
				}
				
			});
			return v;
		}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
	}
	
	
	
}