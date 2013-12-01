package org.mpto.montebook;



import java.util.List;

import org.mpto.montebook.model.MonteSchool;

import com.activeandroid.extras.ModelLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SchoolDetailsFragment extends Fragment {
	private View fragment;
	//private MonteSchool schoolDetails;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragment = inflater.inflate(R.layout.school_details, container, false);
		
//		schoolDetails = new MonteSchoolService();
//		schoolDetails.addObserver(this);
		
		//schoolDetails = MonteSchool.getSchool();

		getActivity().getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<List<MonteSchool>>() {
		    @Override
		    public Loader<List<MonteSchool>> onCreateLoader(int id, Bundle args)
		    {
		        return new ModelLoader<MonteSchool>(getActivity(), MonteSchool.class, true);
		    }

		    @Override
		    public void onLoadFinished(Loader<List<MonteSchool>> loader, List<MonteSchool> data)
		    {
		    	if (data.size() > 0) {
		    		MonteSchool theSchool = data.get(0);
		    		
		    		TextView name = (TextView) fragment.findViewById(R.id.auth_tvSchoolName);
		    		name.setText(theSchool.name);
		    		
		    		TextView addr1 = (TextView) fragment.findViewById(R.id.auth_tvSchoolAddress1);
		    		addr1.setText(theSchool.street);
		    		
		    		TextView addr2 = (TextView) fragment.findViewById(R.id.auth_tvSchoolAddress2);
		    		addr2.setText(theSchool.city + ", " + theSchool.state + " " + theSchool.zip);
		    		
		    		TextView phone = (TextView) fragment.findViewById(R.id.auth_tvSchoolPhone);
		    		phone.setText(PhoneNumberUtils.formatNumber(theSchool.phone));
		    		
		    		TextView principal = (TextView) fragment.findViewById(R.id.auth_tvSchoolPrincipal);
		    		principal.setText(getResources().getString(R.string.principal_label) + " " +
		    					theSchool.principalSalutation + " " + 
		    					theSchool.principalFirstName + " " + 
		    					theSchool.principalLastName);
		    		
		    		TextView email = (TextView) fragment.findViewById(R.id.auth_tvSchoolPrincipalEmail);
		    		email.setText(theSchool.principalEmail);
		    	}
		    }

		    @Override
		    public void onLoaderReset(Loader<List<MonteSchool>> loader)
		    {
		    	Log.i("argh", "bar");
		    }
		});
		
		return fragment;
	}
/*
	@Override
	public void update(Observable observable, Object data) {
		// this is where we would take down the splash screen and then call the
		// first fragment


	}
	*/
}
