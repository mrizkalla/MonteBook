package org.mpto.montebook;

import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SchoolDetailsFragment extends Fragment implements Observer {
	private View fragment;
	private MonteSchool schoolDetails;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragment = inflater.inflate(R.layout.school_details, container, false);
		
		schoolDetails = new MonteSchool();
		schoolDetails.addObserver(this);
		
		return fragment;
	}

	@Override
	public void update(Observable observable, Object data) {
		// this is where we would take down the splash screen and then call the
		// first fragment
		TextView name = (TextView) fragment.findViewById(R.id.auth_tvSchoolName);
		name.setText(schoolDetails.getName());
		
		TextView addr1 = (TextView) fragment.findViewById(R.id.auth_tvSchoolAddress1);
		addr1.setText(schoolDetails.getAddress1());
		
		TextView addr2 = (TextView) fragment.findViewById(R.id.auth_tvSchoolAddress2);
		addr2.setText(schoolDetails.getAddress2());
		
		TextView phone = (TextView) fragment.findViewById(R.id.auth_tvSchoolPhone);
		phone.setText(schoolDetails.getPhone());

	}
}
