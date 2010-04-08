package com.jittr.android.gamemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomizePublicGameActivity extends Activity {
	private Button cancelButton;
	private Button doneButton;
	private TextView titleText;
	private TextView dateText;
	private RadioGroup teamRadioGroup;
	private RadioButton team1Radiobutton;
	private RadioButton team2RadioButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_game);
        setUpViews();
    }

	@Override
	protected void onResume() {
	   super.onResume();
	   titleText.setText("New York Yankees Versus Mets");
	   dateText.setText("June 1,2010 7pm EST");
	}
	
	public void cancelButtonClicked(View v) {
		finish();
	}
	private void setUpViews() {
		cancelButton = (Button) findViewById(R.id.cancel_button);
        doneButton = (Button)findViewById(R.id.done);
        titleText = (TextView)findViewById(R.id.title);
        dateText = (TextView)findViewById(R.id.gamedate);
        teamRadioGroup = (RadioGroup)findViewById(R.id.teamradiogroup);
        team1Radiobutton = (RadioButton)findViewById(R.id.team1);
        team2RadioButton = (RadioButton)findViewById(R.id.team2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelButtonClicked(v);
			}
		});

	}

} //class
