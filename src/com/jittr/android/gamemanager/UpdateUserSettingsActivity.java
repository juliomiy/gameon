package com.jittr.android.gamemanager;

//import Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.jittr.android.gamemanager.GameOnActivity;

public class UpdateUserSettingsActivity extends GameOnActivity {

	private EditText twitterEditText;
	private EditText facebookEditText;
	private EditText foursquareEditText;
	private CheckBox twitterCheckBox;
	private CheckBox facebookCheckBox;
	private GameUserSettings userSettings;
	private Button saveButton;
	private Button cancelButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setUpViews();
    } //onCreateBundle

	protected void saveSettings(View v) {
		userSettings.setFacebook(facebookEditText.getText().toString());
		userSettings.setTwitter(twitterEditText.getText().toString());
		userSettings.setFoursquare(foursquareEditText.getText().toString());
        userSettings.setDefaultFacebook(facebookCheckBox.isChecked());
        userSettings.setDefaultTwitter(twitterCheckBox.isChecked());
        
		getStuffApplication().saveSettings(userSettings);
		finish();
	} //saveSettings

	private void setUpViews() {
		twitterEditText = (EditText)findViewById(R.id.twitter);
		facebookEditText=(EditText)findViewById(R.id.facebook);
		foursquareEditText=(EditText)findViewById(R.id.foursquare);
		twitterCheckBox=(CheckBox)findViewById(R.id.defaulttwitter);
		facebookCheckBox=(CheckBox)findViewById(R.id.defaultfacebook);
		saveButton=(Button)findViewById(R.id.savesettings);
		cancelButton=(Button)findViewById(R.id.cancelsettings);
		
	    userSettings = new GameUserSettings();
		twitterEditText.setText(userSettings.getTwitter());
		facebookEditText.setText(userSettings.getFacebook());
		foursquareEditText.setText(userSettings.getFoursquare());
		twitterCheckBox.setChecked(userSettings.isDefaultTwitter());
		facebookCheckBox.setChecked(userSettings.isDefaultFacebook());
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}

			private void cancel() {
				finish();
			}
		});
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveSettings(v);
			}
		});

	} //setUpViews
}  //class
