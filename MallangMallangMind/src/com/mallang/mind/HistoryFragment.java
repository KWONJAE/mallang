package com.mallang.mind;

import com.mallang.mind.db.DbOpenHelper;
import com.mallang.mind.db.MallangData;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryFragment extends Fragment{

	
	private DbOpenHelper mDbOpenHelper;
	ListView List;
    private SharedPreferences pref;	
	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDbOpenHelper = new DbOpenHelper(this.getActivity().getBaseContext());
		pref = this.getActivity().getSharedPreferences("pref", this.getActivity().MODE_PRIVATE);
		String userID = pref.getString("userID", "");
		View v = inflater.inflate(R.layout.historylayout, container, false);
		List = (ListView)v.findViewById(R.id.historylist);     
		        
		SampleAdapter adapter; 
		adapter = new SampleAdapter(v.getContext());
		mDbOpenHelper.open();
		try {
			Cursor logCursor = mDbOpenHelper.getLogs(userID);
			mDbOpenHelper.close();
			if(logCursor==null)
				return v;
			
			String date = null, logTime = null, mediType = null;
			String mediAmount = null;
			int typeFlag=0;
			logCursor.moveToFirst();
			do{
				date =logCursor.getString(logCursor.getColumnIndex(MallangData.CreateLogDB.LOG_YMDHM));
				//yyyyMMddHHmm
				logTime = date.substring(8,12);
				logTime = " "+logTime.substring(0, 2)+":"+logTime.substring(2);
				date = date.substring(0, 4)+"/"+date.substring(4, 6)+"/"+date.substring(6,8);
				typeFlag = logCursor.getInt(logCursor.getColumnIndex(MallangData.CreateLogDB.MEDI_TYPE));
				mediType="같이하기";
				if(typeFlag==1)
					mediType="혼자하기";
				
				mediAmount = logCursor.getString(logCursor.getColumnIndex(MallangData.CreateLogDB.TIME_AMOUNT));
				adapter.add(new ListItem(date,logTime,mediType,mediAmount));
			}while(logCursor.moveToNext());
			
			List.setAdapter(adapter);
			
			return v;
		} catch(CursorIndexOutOfBoundsException e) {
			return v;
		}
	}

	
	private class ListItem {
		public String date;
		public String logTime;
		public String mediType;
		public String mediAmount;
		
		public ListItem(String date,String logTime, String mediType, String mediAmount) {
			this.mediType = mediType; 
			this.logTime = logTime;
			this.date = date;
			this.mediAmount = mediAmount;
		}
	}

	public class SampleAdapter extends ArrayAdapter<ListItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.historylistlayout, null);
			}
			TextView date  = (TextView) convertView.findViewById(R.id.history_date);
			date.setText(getItem(position).date);
			TextView logTime  = (TextView) convertView.findViewById(R.id.history_logtime);
			logTime.setText(" "+getItem(position).logTime);
			TextView mediType  = (TextView) convertView.findViewById(R.id.history_meditype);
			mediType.setText(" "+getItem(position).mediType+" ");
			TextView mediAmount = (TextView) convertView.findViewById(R.id.history_mediamount);
			mediAmount.setText(" "+getItem(position).mediAmount+"분");
			return convertView;
		}
	
	}
}
