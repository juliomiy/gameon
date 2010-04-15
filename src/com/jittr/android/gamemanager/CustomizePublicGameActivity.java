package com.jittr.android.gamemanager;

import com.jittr.android.gamemanager.R;
import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.gamemanager.GameUserSettings;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomizePublicGameActivity extends Activity {
	public static final String CUSTOMIZE_GAME = "customize_game";
	private Button cancelButton;
	private Button doneButton;
	private TextView titleText;
	private TextView dateText;
	private RadioGroup teamRadioGroup;
	private RadioButton team1Radiobutton;
	private RadioButton team2Radiobutton;
	private Intent callingIntent;
	private CheckBox facebook;
	private CheckBox twitter;
	private CheckBox foursquare;

	private Game game;   //selected game to customize - currently only 2 team sports
    private GameUserSettings userSettings ;//= new GameUserSettings();  //user settings - currently hardcoded - would like 1 instance variable that doesn't need to read from sqlite except on startup and modification
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_game);
        userSettings = ((GameManagerApplication) getApplication()).getGameUserSettings();
        setUpViews();
    }

	@Override
	protected void onResume() {
	   super.onResume();
	   callingIntent = super.getIntent();
       if (callingIntent != null) {
          game = callingIntent.getParcelableExtra(CUSTOMIZE_GAME);
       	  if (game != null) {
       		  titleText.setText(game.getName());
       		  team1Radiobutton.setText(game.getHomeTeam());
       		  team2Radiobutton.setText(game.getHomeTeam());
       		  dateText.setText(game.getEventDate());
       	  }
       }
     //  userSettings = getStuffApplication().getGameUserSettings();
       if (userSettings != null ) {
          twitter.setChecked(userSettings.isDefaultTwitter());
          facebook.setChecked(userSettings.isDefaultFacebook());
          foursquare.setChecked(userSettings.isDefaultFoursquare());
       }
	} //onResume
	
	protected void cancelButtonClicked(View v) {
		setResult(RESULT_CANCELED,callingIntent);
		finish();
	}  //cancelButtonClicked

	
	protected void doneButtonClicked(View v) {
		callingIntent.putExtra(CUSTOMIZE_GAME,game);
		setResult(RESULT_OK, callingIntent); 	
		finish();
	}  //doneButtonClicked
	
	private void setUpViews() {
		cancelButton = (Button) findViewById(R.id.cancel_button);
        doneButton = (Button)findViewById(R.id.done);
        titleText = (TextView)findViewById(R.id.title);
        dateText = (TextView)findViewById(R.id.gamedate);
        teamRadioGroup = (RadioGroup)findViewById(R.id.teamradiogroup);
        team1Radiobutton = (RadioButton)findViewById(R.id.team1);
        team2Radiobutton = (RadioButton)findViewById(R.id.team2);
        facebook=(CheckBox)findViewById(R.id.facebook);
        twitter=(CheckBox)findViewById(R.id.twitter);
        foursquare=(CheckBox)findViewById(R.id.foursquare);

        cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelButtonClicked(v);
			}
		});
        
        doneButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				doneButtonClicked(v);
			}
		});
        
        facebook.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox)v).isChecked()) {
					game.setFacebookNetwork(userSettings.getFacebook());
				}
				else 
					game.setFacebookNetwork("");
			}
		});
        twitter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox)v).isChecked()) {
					game.setTwitterNetwork(userSettings.getTwitter());
				} else
					game.setTwitterNetwork("");
			}
		});
        foursquare.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox)v).isSelected()) {
					game.setFoursquareNetwork(userSettings.getFoursquare());
				} else
					game.setFoursquareNetwork("");
			}
		});
	}

	

} //class
