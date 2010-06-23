package com.jittr.android.gamemanager;

import com.jittr.android.gamemanager.GameOnOAuth;
import com.jittr.android.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class testOAuthActivity extends GameOnActivity {
	private Button cancelButton;
	private Button testoauthButton;
    private GameOnOAuth OAuth;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testoauth);
        setUpViews();
    }

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void setUpViews() {
		cancelButton = (Button)findViewById(R.id.cancel_button);	
		testoauthButton = (Button)findViewById(R.id.testoauth);	
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
		testoauthButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				testoauth();
			}
		});

	}

	protected void testoauth() {
		OAuth = new GameOnOAuth();
		OAuth.Step1();
		Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(OAuth.getProviderUrl()));
	    //Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(OAuth.getProviderUrl()); 
		startActivity(intent); 
        finish();		
	}

	protected void cancel() {
		finish();	
	}
}  //class testOAuthActivity
