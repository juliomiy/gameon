package com.jittr.android.gamemanager;

import java.sql.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jittr.android.gamemanager.GameOnOAuth;
import com.jittr.android.webservice.TwitterAPIs;

public class testTwitterStatusUpdateActivity extends GameOnActivity {

	private GameOnOAuth OAuth = new GameOnOAuth();
	private String twitterStatusUpdate="this is a test of status update";
	private Button cancelButton;
	private Button testTwitterStatusUpdateButton;
	private GameUserSettings userSettings;
   
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testtwitterstatusupdate);
        setUpViews();
    }

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void cancel() {
		finish();
	} //cancel
	
	private void setUpViews() {
		cancelButton = (Button)findViewById(R.id.cancel_button);	
		testTwitterStatusUpdateButton = (Button)findViewById(R.id.testtwitterstatusupdate);	
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
		testTwitterStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				teststatusupdate();
			}
		});

	}

	protected void teststatusupdate() {
		userSettings = getStuffApplication().getGameUserSettings();
	    String OAuthToken=userSettings.getTwitterOAuthToken();
		String OAuthTokenSecret=userSettings.getTwitterOAuthTokenSecret();
		//	OAuth.sendTwitterUpdate(twitterStatusUpdate, OAuthToken, OAuthTokenSecret);
    //		OAuth.sendTwitterUpdate(twitterStatusUpdate,userSettings.getTwitterOAuthToken(),userSettings.getTwitterOAuthTokenSecret());
		TwitterAPIs testTwitterUpdate = new TwitterAPIs();
		testTwitterUpdate.sendTwitterUpdate(OAuthToken, OAuthTokenSecret,"oauth update from Android " + System.currentTimeMillis());
	}
}
