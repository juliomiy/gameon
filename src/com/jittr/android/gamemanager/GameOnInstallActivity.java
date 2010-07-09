package com.jittr.android.gamemanager;

import com.jittr.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/* this activity fires when the application is installed on an android powered handset
 * used to setup databases, sharedPreferences, user Credentials
 */
public class GameOnInstallActivity extends Activity {
	private RadioGroup radiogroup;
	private ImageView imageview;
	private RadioButton select_facebook;
	private RadioButton select_twitter;
	private RadioButton select_foursquare;
	private Button cancelButton;
	private Button continueButton;
	private OnCheckedChangeListener onCheckedChangeListener;
	private int socialNetworkSelected = 0;
	private int facebook_radioID;
	private int twitter_radioID;
	private int foursquare_radioID;
	
	private final int SIGNUP_FACEBOOK=1;
	private final int SIGNUP_TWITTER=2;
	private final int SIGNUP_FOURSQUARE=3;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.install);
        setUpViews();
       // twitter = new GOTwitterWrapper();
       // foursquare = new GOFoursquare();
    }
	private void setUpViews() {
		    imageview = (ImageView)findViewById(R.id.logo);
	        radiogroup = (RadioGroup)findViewById(R.id.socialnetworkselection);
	        select_facebook = (RadioButton)findViewById(R.id.select_facebook);
	        select_twitter = (RadioButton)findViewById(R.id.select_twitter);
	        select_foursquare = (RadioButton)findViewById(R.id.select_foursquare);
	        continueButton = (Button) findViewById(R.id.continue_button);
	        cancelButton = (Button)findViewById(R.id.cancel);
	        
	        facebook_radioID = select_facebook.getId();
	        twitter_radioID = select_twitter.getId();
	        foursquare_radioID = select_foursquare.getId();
	        
	        onCheckedChangeListener = new OnCheckedChangeListener() {
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	               continueButton.setEnabled(true);
	               if (checkedId ==facebook_radioID)
	                  socialNetworkSelected=SIGNUP_FACEBOOK;
	               else if (checkedId == twitter_radioID)
	                  socialNetworkSelected=SIGNUP_TWITTER;
	               else if (checkedId == foursquare_radioID)
	                  socialNetworkSelected = SIGNUP_FOURSQUARE;
	            	//	group.
	            	Log.i("Test", "onCheckedChanged() id:" + checkedId + " social network selected = " + socialNetworkSelected);
	            }
	        };
	        radiogroup.setOnCheckedChangeListener(onCheckedChangeListener); 
	        
	        continueButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					continueSignUp(v);
				}
			});
	        
	        cancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
	                cancelSignUp(v);
				}
			});		
	}  //setupViews
	
	private void continueSignUp(View v) {
	    switch (socialNetworkSelected) {
	       case SIGNUP_FACEBOOK:
	   // 	  authorizeFacebook(v);  
	          break;
	       case SIGNUP_TWITTER:
	    	  authorizeTwitter(v);
	          break;
	       case SIGNUP_FOURSQUARE:
	    	  // getFoursquareCredentials(v);
	    	  authorizeFoursquare();
	          break;
	    } //switch
	} //continueSignUp

	private void authorizeFoursquare() {
		Intent intent = new Intent(this,GameOnAuthorizationActivity2.class);
		startActivity(intent);
	}
	//oauth protocol for obtaining user authorization on the user's behalf
	private void authorizeTwitter(View v) {
		Intent intent = new Intent(this,GameOnAuthorizationActivity.class);
		startActivity(intent);
	} //authorizeTwitter
	
	private void cancelSignUp(View v) {
	    finish();	
	} //cancelSignup
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}  //class 
