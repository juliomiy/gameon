package com.jittr.android.twitter;

import java.sql.Date;

import com.jittr.android.gamemanager.GameManagerApplication;
import com.jittr.android.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jittr.android.gamemanager.GameOnOAuth;
import com.jittr.android.webservice.TwitterAPIs;

public class testTwitterStatusUpdateActivity extends Activity {

	private String twitterStatusUpdate="this is a test of status update";
	private Button cancelButton;
	private Button testTwitterStatusUpdateButton;
	private Context context;
	private GOTwitterWrapper twitter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GameManagerApplication.appContext;
        twitter = new GOTwitterWrapper(context);
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
		twitter.sendTwitterUpdate("oauth update from Android " + System.currentTimeMillis());
	}
}
