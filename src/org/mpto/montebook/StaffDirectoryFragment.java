package org.mpto.montebook;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StaffDirectoryFragment extends ListFragment {
	private View fragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragment = inflater.inflate(R.layout.staff_directory, container, false);
		
		ArrayList<String> items = new ArrayList<String>();
		items.add("Item 1");
		items.add("Item 2");
		items.add("Item 3");
		items.add("Item 4");
		items.add("Item 5");
		items.add("Item 6");
		items.add("Item 7");
		items.add("Item 8");
		ArrayAdapter<String> aItems = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, items);
		
		setListAdapter(aItems);
		
		
		
		
		return fragment;
	}
}
