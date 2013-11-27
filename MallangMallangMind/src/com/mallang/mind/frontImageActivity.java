package com.mallang.mind;

import com.mallang.mind.login.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class frontImageActivity extends Activity{

	Handler h;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.frontimagelayout);
		h= new Handler();
		
		h.postDelayed(irun, 2000);
		
		
		
		
	}

	Runnable irun = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent i = new Intent(frontImageActivity.this,LoginActivity.class);
			startActivity(i);
			finish();
			
			//fade mode
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	};

	// 중간에 뒤로가기 버튼 눌러서 꺼졌을 때 2초 후 메인 페이지가 뜨는 것을 방지
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		h.removeCallbacks(irun);
	}
	
	
	
	
	
	
}
