package org.mpto.montebook;

import com.parse.Parse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class AuthActivity extends FragmentActivity {
	public MBDataResultReceiver mResultReceiver;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authenticate);
	    setupServiceReceiver();

		//Initialize Parse
		Parse.initialize(this, "lSE4EALkyeHNK80pmxwozcPcwZTYLgMMauKvOb5b", "f3sfQ3yIW0N958l55kOwD1YvPzXmB6CujT0tsnRS"); 

		onStartService();
	}
        
    // Starts the IntentService
    public void onStartService() {
      Intent i = new Intent(this, MonteBookIntentService.class);
      i.putExtra("receiver", mResultReceiver);
      startService(i);
    }

    // Setup the callback for when data is received from the service
    public void setupServiceReceiver() {
    	mResultReceiver = new MBDataResultReceiver(new Handler());
    	// This is where we specify what happens when data is received from the service
    	mResultReceiver.setReceiver(new MBDataResultReceiver.Receiver() {

    		@Override
	        public void onReceiveResult(int resultCode, Bundle resultData) {
	          if (resultCode == RESULT_OK) {
	            // Check whether the activity is using the layout version with
	            // the fragment_container FrameLayout. If so, we must add the first fragment
	            if (findViewById(R.id.fragment_container) != null) {
	
	                // However, if we're being restored from a previous state,
	                // then we don't need to do anything and should return or else
	                // we could end up with overlapping fragments.
	                //if (savedInstanceState != null) {
	                //    return;
	                //}
	
	                // Create an instance of ExampleFragment
	                SchoolDetailsFragment firstFragment = new SchoolDetailsFragment();
	
	                // In case this activity was started with special instructions from an Intent,
	                // pass the Intent's extras to the fragment as arguments
	                firstFragment.setArguments(getIntent().getExtras());
	
	                // Create an instance of ExampleFragment
	                StaffDirectoryFragment secondFragment = new StaffDirectoryFragment();
	
	                // In case this activity was started with special instructions from an Intent,
	                // pass the Intent's extras to the fragment as arguments
	                secondFragment.setArguments(getIntent().getExtras());
	
	                // Add the fragment to the 'fragment_container' FrameLayout
	                getSupportFragmentManager().beginTransaction()
	                        .add(R.id.fragment_container, firstFragment)
	                        .add(R.id.fragment_container2, secondFragment)
	                        .commit();
	            }          
	          }
            }
        });

	}

}
