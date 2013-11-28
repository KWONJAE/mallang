package com.mallang.mind;

import com.mallang.mind.db.DbOpenHelper;
import com.mallang.mind.db.UserInfo;
import com.mallang.mind.db.UserMallang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyInfoFragment extends Fragment{

	private DbOpenHelper mDbOpenHelper;
    private SharedPreferences pref;
    private TextView id, name, birthSex, regTime, count, time, mallang;
	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDbOpenHelper = new DbOpenHelper(this.getActivity().getBaseContext());
		pref = this.getActivity().getSharedPreferences("pref", this.getActivity().MODE_PRIVATE);
		String userID = pref.getString("userID", "");
		View v = inflater.inflate(R.layout.mypagelayout, container, false);
		id = (TextView)v.findViewById(R.id.mypage_id);
		name = (TextView)v.findViewById(R.id.mypage_name);
		birthSex = (TextView)v.findViewById(R.id.mypage_birth_sex);
		regTime = (TextView)v.findViewById(R.id.mypage_regi_time);
		count = (TextView)v.findViewById(R.id.mypage_count);
		time = (TextView)v.findViewById(R.id.mypage_time);
		mallang = (TextView)v.findViewById(R.id.mypage_mallang_point);
		id.setText(userID);
		
		mDbOpenHelper.open();
		UserInfo tempInfo = mDbOpenHelper.getUserInfo(userID);
		UserMallang mallangOne = mDbOpenHelper.getUserMallang(userID, 1);
		UserMallang mallangTwo = mDbOpenHelper.getUserMallang(userID, 2);
		mDbOpenHelper.close();
		
		if(tempInfo!=null) {
			name.setText(tempInfo.getName());
			birthSex.setText(tempInfo.getBirthDate()+" & "+tempInfo.getGender());
			regTime.setText(String.valueOf(tempInfo.getRegDate()));
		}
		int countSum=0;
		int timeSum=0, mallangSum=0;
		if(mallangOne!=null) {
			countSum=mallangOne.getMediCount();
			timeSum=mallangOne.getMediTime();
			mallangSum=mallangOne.getChakra();
		}
		if(mallangTwo!=null) {
			countSum+=mallangTwo.getMediCount();
			timeSum+=mallangTwo.getMediTime();
			mallangSum+=mallangTwo.getChakra();
		}
		if(countSum>0){
			count.setText(String.valueOf(countSum)+"번");
			time.setText("Total Time : "+String.valueOf(timeSum)+"분");
			mallang.setText(String.valueOf(mallangSum));
		}
		return v;
	}

	
}
