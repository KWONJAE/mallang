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

	// �߰��� �ڷΰ��� ��ư ������ ������ �� 2�� �� ���� �������� �ߴ� ���� ����
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		h.removeCallbacks(irun);
	}
	
	
	
	
	
	
}
