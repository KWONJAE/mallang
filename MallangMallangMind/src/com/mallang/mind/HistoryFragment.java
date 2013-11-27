package com.mallang.mind;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryFragment extends Fragment{

	
	ListView List;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.historylayout, container, false);
			  List = (ListView)v.findViewById(R.id.historylist);     
		        
			 SampleAdapter adapter; 
			 adapter = new SampleAdapter(v.getContext());
			        /////********************************** 요기다 넣기
			       
			        adapter.add(new ListItem("2013/11/25","3회","5회"));
			        adapter.add(new ListItem("2013/11/26","3회","5회"));
			        adapter.add(new ListItem("2013/11/27","3회","5회"));
			        /////********************************** 요기다 넣기
			        List.setAdapter(adapter);
			      
			
		return v;
		
	}

	
	private class ListItem {
		public String date;
		public String numofone;
		public String numoftwo;
		
		public ListItem(String date,String numofone, String numoftwo) {
			this.numoftwo = numoftwo; 
			this.numofone = numofone;
			this.date = date;
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
			TextView date  = (TextView) convertView.findViewById(R.id.date);
			date.setText(getItem(position).date);
			TextView twoText  = (TextView) convertView.findViewById(R.id.onetrynum);
			twoText.setText(getItem(position).numoftwo);
			TextView oneText  = (TextView) convertView.findViewById(R.id.twotrynum);
			oneText.setText(getItem(position).numofone);
			return convertView;
		}
	
	
	
	
	
	
	
	}
}
