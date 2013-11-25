package com.mallang.mind.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.mallang.mind.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InfoRegisterActivity extends Activity{
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
    private String url = "http://192.168.123.186:8080/mallangDB/app/registerInfo";
    //to save yyyy-mm-dd
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateSetListener;
    
    private int gender;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_register_layout);
		
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
		Intent receivedIntent = getIntent();
		userID = receivedIntent.getStringExtra("userID");
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_selection,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genderSpinner.setAdapter(adapter);
		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),
						"선택한 성별은 "+parent.getItemAtPosition(position)
						+"  : "+parent.getItemIdAtPosition(position),
						Toast.LENGTH_SHORT).show();
				gender = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Toast.makeText(parent.getContext(),
						"선택하지 않았습니다 ",
						Toast.LENGTH_SHORT).show();
				gender = 3;
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
				Toast.makeText(InfoRegisterActivity.this, birthInput.getText().toString(),
		                 Toast.LENGTH_SHORT).show() ;
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
	}
	private final Handler handler = new Handler() {
	      @Override
	      public void handleMessage(Message msg) {
	            pDialog.dismiss();
	            String result=msg.getData().getString("RESULT");
				if ( result.equals("success") ) {
				  Toast.makeText(InfoRegisterActivity.this, "정보 저장 완료",
				                 Toast.LENGTH_SHORT).show() ;
				  finish();
				} else {
				      Toast.makeText(InfoRegisterActivity.this, "추가 정보 저장 실패",
				                     Toast.LENGTH_LONG).show() ;    
				        }
	      }
	      
	    };
	    
	//실패하는 경우 <result>failed</result>를 반환하도록 설정해 두었다. 
	public String parsingData(InputStream input){
	    String result=null;
	    try {
	         XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
	         XmlPullParser parser = factory.newPullParser();
	         parser.setInput(new InputStreamReader(input));
	         while ( parser.next() != XmlPullParser.END_DOCUMENT) {
	             String name=parser.getName();
	              if ( name != null && name.equals("result"))
	                         result=parser.nextText();
	              }
	         }catch(Exception e){e.printStackTrace();}
	         return result;
	     }
	 
	 public void saveInfoProcess() {
	      final ResponseHandler<String> responseHandler=
	           new ResponseHandler<String>() {
	             @Override
	             public String handleResponse(HttpResponse response)
	                                              throws ClientProtocolException, IOException {
	                     String result=null;
	                     HttpEntity entity=response.getEntity(); 
	                     result=parsingData(entity.getContent());
	                     Message message=handler.obtainMessage();
	                     Bundle bundle=new Bundle();
	                     if ( result.equals("success") ) 
	                    	 bundle.putString("RESULT", "success");
						 else
							 bundle.putString("RESULT", "failed");
	                     
		                 message.setData(bundle);
		                 handler.sendMessage(message);
		                 return result;
	                 }
	      		};
			     // 로그인이 처리되고 있다는 다이얼로그를 화면에 표시한다.
			 pDialog=ProgressDialog.show(this, "", "Now Loading....");
			 
			 // 서버에 HTTP 처리 요청은 새로운 스레드를 생성하여 비동기식으로 처리하는것이 효율적이다.
			 new Thread() {
			       @Override
			        public void run() {
						 HttpClient http = new DefaultHttpClient();
						 try { 
						     // 서버에 전달할 파라메터 세팅   
							 ArrayList<NameValuePair> nameValuePairs = 
							         new ArrayList<NameValuePair>();
							 nameValuePairs.add(new BasicNameValuePair
									 ("userID", userID));
							 String userInfo = nickInput.getText().toString() + "&"
									 + gender + "&"
									 + birthInput.getText().toString() + "&"
									 + nationalInput.getText().toString() + "&"
									 + cityInput.getText().toString();
		
							 nameValuePairs.add(new BasicNameValuePair
									 ("userInfo", userInfo));
							 HttpParams params = http.getParams();
							 HttpConnectionParams.setConnectionTimeout(params, 5000);
							 HttpConnectionParams.setSoTimeout(params, 5000);
							 HttpPost httpPost = new HttpPost(url);
							 UrlEncodedFormEntity entityRequest = 
				                     new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
				             httpPost.setEntity(entityRequest);
				             http.execute(httpPost,responseHandler); 
			            }catch(Exception e){e.printStackTrace();}
			       }
			  }.start();    //스레드를 실행시킨다.
	 }
}
