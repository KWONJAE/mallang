package com.mallang.mind.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import org.apache.logging.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.mallang.mind.R;
import com.mallang.mind.db.DbOpenHelper;
import com.mallang.mind.db.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	private static final String TAG = "RegisterActivity";
	private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private User userClass;
    private ArrayList<User> mUserArray;
    
	private EditText idInput;
	private EditText nameInput;
    private EditText pwInput;
    private EditText pwConfirmInput;
    private ProgressDialog pDialog;
    private Button registerBtn;
    private Button cancleBtn;
    private String url = "http://192.168.123.186:8080/mallangDB/app/registerID";
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
					Toast toast = Toast.makeText(getBaseContext(), "Password가 일치하지 않습니다", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				registerProcess();
			}
			
		});
		cancleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
	}
	private final Handler handler = new Handler() {
	      @Override
	      public void handleMessage(Message msg) {
	            pDialog.dismiss();
	            String result=msg.getData().getString("RESULT");
				if ( result.equals("success") ) {
				  Toast.makeText(RegisterActivity.this, "회원가입 완료",
				                 Toast.LENGTH_SHORT).show() ;
				  Intent intent = new Intent(getBaseContext(), InfoRegisterActivity.class);
				  intent.putExtra("userID", idInput.getText().toString());
				  startActivity(intent);
				  finish();
				} else if (result.equals("sameID")) {
					Toast.makeText(RegisterActivity.this, "같은 ID가 있습니다",
		                     Toast.LENGTH_LONG).show() ;
				} else {
				      Toast.makeText(RegisterActivity.this, "회원가입 실패",
				                     Toast.LENGTH_LONG).show() ;    
				        }
	      }
	      
	    };
	public boolean checkPasswd() {
		String pw = pwInput.getText().toString();
		String pwConfirm = pwConfirmInput.getText().toString();
		if(pw.equals(pwConfirm))
			return true;
		return false;
	}
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
	 
	 public void registerProcess() {
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
	                     else if(result.equals("sameID"))
	                    	 bundle.putString("RESULT", "sameID");
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
									 ("userID", idInput.getText().toString()));
							 nameValuePairs.add(new BasicNameValuePair
									 ("userPasswd", pwInput.getText().toString()));
							 nameValuePairs.add(new BasicNameValuePair
									 ("userName", nameInput.getText().toString()));
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
