package com.jittr.android.twitter;

import java.sql.Date;

import com.jittr.android.gamemanager.GameManagerApplication;
import com.jittr.android.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jittr.android.gamemanager.GameOnOAuth;
import com.jittr.android.webservice.TwitterAPIs;

public class testTwitterStatusUpdateActivity extends Activity {

	private String twitterStatusUpdate="this is a test of status update";
	private Button cancelButton;
	private Button testTwitterStatusUpdateButton;
	protected ProgressDialog progressDialog;
	private Context context;
	private GOTwitterWrapper twitter;
	final private Handler handler = new Handler();
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GameManagerApplication.appContext;
        twitter = new GOTwitterWrapper(context);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.testtwitterstatusupdate);
        setUpViews();
    }
	private Runnable finishedTwitterThread = new Runnable() {
	        public void run() {
	          finishedTwitter();
	        }
	};
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	protected void finishedTwitter() {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
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
			      progressDialog = new ProgressDialog(testTwitterStatusUpdateActivity.this);
			      progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			      progressDialog.setMessage(getResources().getString(R.string.test_twitter_update));
			      progressDialog.setCancelable(false);
			      progressDialog.show();
			      Thread twitterThread = new Thread() {
			 	      public void run() {
			 	        teststatusupdate();
			 	        handler.post(finishedTwitterThread);
			 	      }  //run
			 	   };  //Thread
			 	   twitterThread.start();
			}
		});   //testTwitterStatus
  
	}  //setUpViews
	protected void teststatusupdate() {
		try {
		    twitter = new GOTwitterWrapper(GameManagerApplication.appContext);
			twitter.sendTwitterUpdate("oauth update from Android " + System.currentTimeMillis());
		} catch (GameOnTwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  //teststatusupdate
} //class
