package org.mpto.montebook;



import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

public class MonteBookIntentService extends IntentService {

	private ParseService theService = new ParseService(this);
	
	public MonteBookIntentService() {
		super("monte-book-service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		// Right now just call this all the time
		theService.getMonteSchoolData();
		theService.getMonteStaffData();
				
		// Need to know if this succeeded or not so I can signal the main thread somehow
		// But for now just signal to the main thread all the time so I can learn how this works
	    // Extract the receiver passed into the service
	    ResultReceiver rec = intent.getParcelableExtra("receiver");
	    // Extract additional values from the bundle
	    // String val = intent.getStringExtra("foo");
	    // To send a message to the Activity, create a pass a Bundle
	    // Bundle bundle = new Bundle();
	    // bundle.putString("resultValue", "My Result Value. Passed in: ");
	    // Here we call send passing a resultCode and the bundle of extras
	    rec.send(Activity.RESULT_OK, null);
	}

}