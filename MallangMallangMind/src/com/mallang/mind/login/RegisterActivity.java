package com.mallang.mind.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mallang.mind.R;
import com.mallang.mind.db.DbOpenHelper;

public class RegisterActivity extends Activity{
	private DbOpenHelper mDbOpenHelper;
    
	private EditText idInput;
	private EditText nameInput;
    private EditText pwInput;
    private EditText pwConfirmInput;
    private Button registerBtn;
    private Button cancleBtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		// DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
		
		idInput = (EditText) findViewById(R.id.register_id_input);
		nameInput = (EditText) findViewById(R.id.register_name_input);
		pwInput = (EditText) findViewById(R.id.register_pw_input);
		pwConfirmInput = (EditText) findViewById(R.id.register_pwconfirm_input);
		registerBtn = (Button) findViewById(R.id.register_btn);
		cancleBtn = (Button) findViewById(R.id.register_cancle_btn);
		
		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!(checkPasswd())) {
					Toast toast = Toast.makeText(getBaseContext(), "Please check the Password", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				registerProcess();
			}
			
		});
		cancleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDbOpenHelper.close();
				finish();
			}
			
		});
	}
	public boolean checkPasswd() {
		String pw = pwInput.getText().toString();
		String pwConfirm = pwConfirmInput.getText().toString();
		if(pw.equals(pwConfirm))
			return true;
		return false;
	}
	 public void registerProcess() {
		String id = idInput.getText().toString();
		String pw = pwInput.getText().toString();
		if( mDbOpenHelper.checkId(id) ) {
			Toast toast = Toast.makeText(getBaseContext(), "Already Registered ID: " + id, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if (mDbOpenHelper.insertPW(id, nameInput.getText().toString(), pw)) {
			Toast toast = Toast.makeText(getBaseContext(), "Registered successfully", Toast.LENGTH_SHORT);
			toast.show();
			Intent intent = new Intent(getBaseContext(), InfoRegisterActivity.class);
			intent.putExtra("userID", idInput.getText().toString());
			startActivity(intent);
			finish();
			mDbOpenHelper.close();
			return;
		}
		
		Toast toast = Toast.makeText(getBaseContext(), "Can't registered please try it again", Toast.LENGTH_LONG);
		toast.show();
		return;
	 }
}
