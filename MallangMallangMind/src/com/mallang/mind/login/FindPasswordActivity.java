package com.mallang.mind.login;

import com.mallang.mind.R;
import com.mallang.mind.db.DbOpenHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindPasswordActivity extends Activity{
	private DbOpenHelper mDbOpenHelper;
	private EditText idInput;
	private EditText nameInput;
	private Button findBtn;
    private Button cancleBtn;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password_layout);
		// DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        
        idInput = (EditText) findViewById(R.id.findpw_id_input);
        nameInput = (EditText) findViewById(R.id.findpw_name_input);
        findBtn = (Button) findViewById(R.id.findpw_btn);
        cancleBtn = (Button) findViewById(R.id.findpw_cancle_btn);
        
        cancleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
        });
        findBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					findingProcess();
			}
        });
    }
    public void findingProcess() {
		String id = idInput.getText().toString();
		String name = nameInput.getText().toString();
		Toast toast;
		if( !mDbOpenHelper.checkId(id) ) {
			toast = Toast.makeText(getBaseContext(), "There is no match ID, please register", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		String password=mDbOpenHelper.getPw(id, name);
		if( password!=null ) {
			toast = Toast.makeText(getBaseContext(), "Your password is "+password, Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		toast = Toast.makeText(getBaseContext(), "Please check your name and ID", Toast.LENGTH_LONG);
		toast.show();
		return;
	 }
}
