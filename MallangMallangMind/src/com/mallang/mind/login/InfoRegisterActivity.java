package com.mallang.mind.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mallang.mind.R;
import com.mallang.mind.db.DbOpenHelper;
import com.mallang.mind.db.UserInfo;


public class InfoRegisterActivity extends Activity{
	private DbOpenHelper mDbOpenHelper;
	protected static final int DATE_DIALOG_ID = 0;
	private EditText nickInput;
	private Spinner genderSpinner;
    private Button birthInput;
    private EditText nationalInput;
    private EditText cityInput;
    private ProgressDialog pDialog;
    private Button saveBtn;
    private Button skipBtn;
    private String userID;
    //to save yyyy-mm-dd
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateSetListener;
    
    private int dateInteger;
    private String gender;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_register_layout);
		
		// DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
		
        Intent receivedIntent = getIntent();
		userID = receivedIntent.getStringExtra("userID");
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_selection,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
		nickInput = (EditText) findViewById(R.id.info_nick_input);
		genderSpinner = (Spinner) findViewById(R.id.info_gender_spinner);
		birthInput = (Button) findViewById(R.id.info_birth_btn);
		nationalInput = (EditText) findViewById(R.id.info_national_input);
		cityInput = (EditText) findViewById(R.id.info_city_input);
		saveBtn = (Button) findViewById(R.id.info_save_btn);
		skipBtn = (Button) findViewById(R.id.info_skip_btn);
		dateSetListener = new DatePickerDialog.OnDateSetListener() {	
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calendar.set(year, monthOfYear, dayOfMonth);
				setLabel();
			}
		};
		
		genderSpinner.setAdapter(adapter);
		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				gender = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),
						"You did not set gender",
						Toast.LENGTH_SHORT).show();
				gender = "X";
				//default value;
			}
			
		});
		birthInput.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(InfoRegisterActivity.this,
					dateSetListener,
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
			
		});
		
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pDialog=ProgressDialog.show(InfoRegisterActivity.this, "", "Now Loading....");
				saveInfoProcess();
			}
			
		});
		skipBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
	}
	private void setLabel() {
		birthInput.setText(new StringBuilder().append(calendar.get(Calendar.YEAR))
				.append("-").append(calendar.get(Calendar.MONTH)+1)
				.append("-").append(calendar.get(Calendar.DAY_OF_MONTH)));
		dateInteger = new Integer( (new StringBuilder().append(calendar.get(Calendar.YEAR))
					.append(calendar.get(Calendar.MONTH)+1).append(calendar.get(Calendar.DAY_OF_MONTH))).toString() );
	}
	 public void saveInfoProcess() {
		 	
			UserInfo userInfo = new UserInfo();
			userInfo.setNick(nickInput.getText().toString());
			userInfo.setGender(gender);
			userInfo.setBirthDate(dateInteger);
			//to get current date
			calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String tempDate = new SimpleDateFormat("yyyyMMdd").format(date);
			int regDate = new Integer(tempDate);
			userInfo.setRegDate(regDate);
			userInfo.setNational(nationalInput.getText().toString());
			userInfo.setCity(cityInput.getText().toString());
			userInfo.setUserID(userID);
			pDialog.dismiss();
			if(mDbOpenHelper.updateInfo(userInfo)) {
				Toast toast = Toast.makeText(getBaseContext(), "Save informaton successfully", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}
			Toast toast = Toast.makeText(getBaseContext(), "Error to save information please try it again", Toast.LENGTH_SHORT);
			toast.show();
			return;
			
	 }
}
