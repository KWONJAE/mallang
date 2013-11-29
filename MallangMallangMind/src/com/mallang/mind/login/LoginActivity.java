package com.mallang.mind.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mallang.mind.MainActivity;
import com.mallang.mind.R;
import com.mallang.mind.db.DbOpenHelper;

public class LoginActivity extends Activity implements OnClickListener {
	private DbOpenHelper mDbOpenHelper;
    private EditText idInput;
    private EditText passwdInput;
    private ImageButton loginBtn;
    private ImageButton registerBtn;
    private ImageButton findPWBtn;
    private ImageButton skipBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		// DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        
		idInput = (EditText) findViewById(R.id.login_id_input);
		passwdInput = (EditText) findViewById(R.id.login_passwd_input);
		loginBtn = (ImageButton) findViewById(R.id.login_btn);
		registerBtn = (ImageButton) findViewById(R.id.login_register_btn);
		findPWBtn = (ImageButton) findViewById(R.id.login_findpw_btn);
		loginBtn.setOnClickListener(this);
		
		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
				startActivity(intent);
			}
			
		});
		skipBtn = (ImageButton) findViewById(R.id.login_skip_btn);
		skipBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDbOpenHelper.close();
				Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
			}
			
		});
		
		findPWBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(), FindPasswordActivity.class);
				startActivity(intent);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		loginProcess();      //濡쒓렇��踰꾪듉���대┃�섎㈃ 濡쒓렇��泥섎━瑜��쒖옉�쒕떎.
	}
	
	public void loginProcess() {
		String id = idInput.getText().toString();
		String pw = passwdInput.getText().toString();
		Toast toast;
		if( !mDbOpenHelper.checkId(id) ) {
			toast = Toast.makeText(getBaseContext(), "There is no match ID, please register", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if( mDbOpenHelper.checkIdPw(id, pw) ) {
			toast = Toast.makeText(getBaseContext(), "Welcome "+id, Toast.LENGTH_SHORT);
			toast.show();
			mDbOpenHelper.close();
			Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
			finish();
			return;
		}
		toast = Toast.makeText(getBaseContext(), "Password is incorrect, please check your password", Toast.LENGTH_LONG);
		toast.show();
		return;
	 }
}
