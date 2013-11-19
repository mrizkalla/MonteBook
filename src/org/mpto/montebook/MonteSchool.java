package org.mpto.montebook;

import java.util.List;
import java.util.Observable;

import android.telephony.PhoneNumberUtils;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class MonteSchool extends Observable {
	ParseObject theSchool;
	
	public MonteSchool() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MonteSchool");
		query.whereEqualTo("name", "Montclaire");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> data, ParseException e) {
		        if (e == null) {		            
		            // Just get the first item in the list for now.  Later maybe use an iterator
		            theSchool = data.get(0);
		            
		            // Tell the observers the data is there
		            setChanged();
		            notifyObservers();
		        } else {
		            Log.d("school", "Error: " + e.getMessage());
		        }
		    }
		});
	}
	
	public String getName() {
		return (theSchool != null ? theSchool.getString("name") : null);
	}
	
	public String getAddress1() {
		return (theSchool != null ? theSchool.getString("street") : null);
	}
	
	public String getAddress2() {
		String ret = null;
		if (theSchool != null) {
			ret = theSchool.getString("city") + ", " + theSchool.getString("state") + "  " + theSchool.getString("zip");
		}
		return ret;
	}
	
	public String getPhone() {
		return (theSchool != null ? PhoneNumberUtils.formatNumber(theSchool.getString("phone")) : null);
	}
	
}
