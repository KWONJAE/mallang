package com.mallang.mind;

import com.mallang.mind.etc.IntroChadActivity;
import com.mallang.mind.etc.TutorialActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListMenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		String[] MenuList= {"My Profile","È¥ÀÚÇÏ±â","°°ÀÌÇÏ±â","History","Tutorial","Â÷µå¸ÛÅº ¼Ò°³"};
		ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, MenuList);
		setListAdapter(Adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		Intent intent = null;
		switch (position) {
		case 0:
			newContent = new MyInfoFragment();
			break;
		case 1:
			newContent = new BasicMeditationFragment();
			break;
		case 2:
			newContent = new TwoMeditationFragment();
			break;
		case 3:
			newContent = new HistoryFragment();
			break;
		case 4:
			intent = new Intent(this.getActivity().getBaseContext(),TutorialActivity.class);
            startActivity(intent);
			break;
		case 5:
			intent = new Intent(this.getActivity().getBaseContext(),IntroChadActivity.class);
            startActivity(intent);
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
	}


}
