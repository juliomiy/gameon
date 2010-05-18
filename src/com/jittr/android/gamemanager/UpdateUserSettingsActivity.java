package com.jittr.android.gamemanager;

//import Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
        userSettings = getStuffApplication().getGameUserSettings();
        setUpViews();
    } //onCreateBundle

	protected void saveSettings(View v) {
		userSettings.setFacebookID(facebookEditText.getText().toString());
		userSettings.setTwitterID(twitterEditText.getText().toString());
		userSettings.setFoursquareID(foursquareEditText.getText().toString());
        userSettings.setFacebookDefault(facebookCheckBox.isChecked());
        userSettings.setTwitterDefault(twitterCheckBox.isChecked());
        
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
		
	  //  userSettings = new GameUserSettings();
		twitterEditText.setText(userSettings.getTwitterID());
		facebookEditText.setText(userSettings.getFacebookID());
		foursquareEditText.setText(userSettings.getFoursquareID());
		twitterCheckBox.setChecked(userSettings.isTwitterDefault());
		facebookCheckBox.setChecked(userSettings.isFacebookDefault());
		
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
