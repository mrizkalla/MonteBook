package org.mpto.montebook;

import java.util.Date;
import java.util.List;

import org.mpto.montebook.model.MonteSchool;
import org.mpto.montebook.model.MonteStaff;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class ParseService {
	public static final int PARSE_SUCCESS = 100;
	public static final int PARSE_FAILURE = -1;
	public static final String SCHOOL_DATA_UPDATED_AT_TAG = "lastCheckedSchoolData";
	public static final String STAFF_DATA_UPDATED_AT_TAG = "lastCheckedStaffData";
	public static final long SCHOOL_DATA_TIMEOUT = 1000L * 60L * 60L * 24L * 178L; // Every 6 months
	public static final long STAFF_DATA_TIMEOUT = 1000L * 60L * 60L * 24L * 178L; // Every 6 months
	
	private Context mContext;
	
	public ParseService(Context c) {
		mContext = c;
	}
	public void getMonteSchoolData() {
		
		// Only check Monte School Data if timeout has passed (once a year)
		SharedPreferences pref =   
			    PreferenceManager.getDefaultSharedPreferences(mContext);
		long lastCheckedSchoolData = pref.getLong(SCHOOL_DATA_UPDATED_AT_TAG, 0);		
		long timeSinceLastChecked = (new Date()).getTime() - lastCheckedSchoolData;
		
		if (lastCheckedSchoolData == 0 || timeSinceLastChecked > SCHOOL_DATA_TIMEOUT) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("MonteSchool");
			query.whereEqualTo("name", "Montclaire");
			query.whereGreaterThan("updatedAt", new Date(lastCheckedSchoolData));
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> data, ParseException e) {
			    	ParseObject retVal = null;
			        if (e == null) {
			        	// Handle the case where nothing comes back because there is no change
			        	if (data.size() != 0) {
				            // Just get the first item in the list for now.  Later maybe use an iterator
				            retVal = data.get(0);	
				            
		            		MonteSchool theSchool = new MonteSchool();
		
		                    theSchool.name = (retVal != null ? retVal.getString("name") : null);
		                    theSchool.street = (retVal != null ? retVal.getString("street") : null);
		                    theSchool.city = (retVal != null ? retVal.getString("city") : null);
		                    theSchool.state = (retVal != null ? retVal.getString("state") : null);
		                    theSchool.zip = (retVal != null ? retVal.getString("zip") : null);
		                    theSchool.phone = (retVal != null ? retVal.getString("phone") : null);
		                    theSchool.principalEmail = (retVal != null ? retVal.getString("principalEmail") : null);
		                    theSchool.principalFirstName = (retVal != null ? retVal.getString("principalFirstName") : null);
		                    theSchool.principalLastName = (retVal != null ? retVal.getString("principalLastName") : null);
		                    theSchool.principalSalutation = (retVal != null ? retVal.getString("principalSalutation") : null);
		                    theSchool.save();
		                    
		                    // Now write into the prefs db that we just got this data so we dont keep getting it
		            		SharedPreferences pref =   
		            			    PreferenceManager.getDefaultSharedPreferences(mContext);
		            		Editor edit = pref.edit();
		                    edit.putLong(SCHOOL_DATA_UPDATED_AT_TAG, (new Date()).getTime());
		                    edit.commit(); 
			        	}
			        } else {
			            Log.d("school", "Error: " + e.getMessage());
			        }
			    }
			});
		}
	}
	
	public void getMonteStaffData() {
		// Only check Monte School Data if timeout has passed (once a year)
		SharedPreferences pref =   
			    PreferenceManager.getDefaultSharedPreferences(mContext);
		long lastCheckedSchoolData = pref.getLong(STAFF_DATA_UPDATED_AT_TAG, 0);		
		long timeSinceLastChecked = (new Date()).getTime() - lastCheckedSchoolData;
		
		if (lastCheckedSchoolData == 0 || timeSinceLastChecked > STAFF_DATA_TIMEOUT) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("MonteStaff");
			query.whereGreaterThan("updatedAt", new Date(lastCheckedSchoolData));
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> data, ParseException e) {
			    	ParseObject retVal = null;
			        if (e == null) {
			        	// Handle the case where nothing comes back because there is no change
			        	if (data.size() != 0) {
			        		ActiveAndroid.beginTransaction();
			        		try {
			        		        for (int i = 0; i < data.size(); i++) {
							            // Just get the first item in the list for now.  Later maybe use an iterator
							            retVal = data.get(i);	
							            
					            		MonteStaff staff = new MonteStaff();
					            		staff.objectId = (retVal != null ? retVal.getString("objectId") : null);
					            		staff.email = (retVal != null ? retVal.getString("email") : null);
					                    staff.extension = (retVal != null ? retVal.getString("extension") : null);
					                    staff.firstName = (retVal != null ? retVal.getString("firstName") : null);
					                    staff.lastName = (retVal != null ? retVal.getString("lastName") : null);
					                    staff.salutation = (retVal != null ? retVal.getString("salutation") : null);
					                    staff.title = (retVal != null ? retVal.getString("title") : null);
					                    staff.save();
					   
			        		        }
			        		        ActiveAndroid.setTransactionSuccessful();
			        		}
			        		finally {
			        		        ActiveAndroid.endTransaction();
			        		}
                 
		                    // Now write into the prefs db that we just got this data so we dont keep getting it
		            		SharedPreferences pref =   
		            			    PreferenceManager.getDefaultSharedPreferences(mContext);
		            		Editor edit = pref.edit();
		                    edit.putLong(STAFF_DATA_UPDATED_AT_TAG, (new Date()).getTime());
		                    edit.commit(); 
			        	}
			        } else {
			            Log.d("school", "Error: " + e.getMessage());
			        }
			    }
			});
		}		
	}
}
