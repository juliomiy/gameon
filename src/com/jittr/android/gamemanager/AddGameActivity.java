package com.jittr.android.gamemanager;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.gamemanager.CustomizePublicGameActivity;
import com.jittr.android.webservice.TwitterAPIs;

import static com.jittr.android.gamemanager.CustomizePublicGameActivity.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//TODO = for user/manually entered game, need to trigger a customize activity
public class AddGameActivity extends GameOnActivity {
	
	public static final int REQUEST_CHOOSE_PUBLIC_GAME = 0;
	public static final int CUSTOMIZE_PUBLIC_GAME = 1;
	
	private EditText gameNameEditText;
	private Button addButton;
	private Button cancelButton;
	private Button viewPublicButton;
	private boolean changesPending;
	private AlertDialog unsavedChangesDialog;
	private GameUserSettings userSettings;
	private Game game;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game);
        setUpViews();
    }

	@Override
	protected void onResume() {
		super.onResume();
		if (null != game) {
           gameNameEditText.setText(game.getName());			
           //game = null;
		}
	//	adapter.forceReload();
	}
	
	protected void addGame() {
		String gameName = gameNameEditText.getText().toString();
		//Game g = new Game(gameName);
		getStuffApplication().addGame(game);
		//game.
		if (game.getTwitterNetwork() != null) {
			userSettings = getStuffApplication().getGameUserSettings();
			TwitterAPIs twitter = new TwitterAPIs();
			String statusUpdate= game.getName();
			twitter.sendTwitterUpdate( userSettings.getTwitterOAuthToken(),userSettings.getTwitterOAuthTokenSecret(),statusUpdate);
		}
		finish();
	}

	public void viewPublicGameClicked(View view) {
		Intent intent = new Intent(AddGameActivity.this, ViewPublicGameActivity.class);
		startActivityForResult(intent,REQUEST_CHOOSE_PUBLIC_GAME);
	} //viewPublicGameActivity

	@Override
	public void onActivityResult(int requestCode,int resultCode, Intent data) {

		if (REQUEST_CHOOSE_PUBLIC_GAME == requestCode && RESULT_OK == resultCode) {
			game = data.getParcelableExtra(ViewPublicGameActivity.PUBLIC_GAME_RESULT);
			/* allow user to customize the game, choose who they are backing */
            if (game != null) {
        		Intent intent = new Intent(AddGameActivity.this, CustomizePublicGameActivity.class);
        		intent.putExtra(CUSTOMIZE_GAME,game);
        		startActivityForResult(intent,CUSTOMIZE_PUBLIC_GAME);
            }
		} else if (CUSTOMIZE_PUBLIC_GAME == requestCode && RESULT_OK == resultCode) {
			    game = data.getParcelableExtra(CustomizePublicGameActivity.CUSTOMIZE_GAME);
		} else {
			super.onActivityResult(requestCode,resultCode,data);
		}
	} //onActivityResult
	
	protected void cancel() {
		if (changesPending) {
			unsavedChangesDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.unsaved_changes_title)
				.setMessage(R.string.unsaved_changes_message)
				.setPositiveButton(R.string.add_game, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						addGame();
					}
				})
				.setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						unsavedChangesDialog.cancel();
					}
				})
				.create();
			unsavedChangesDialog.show();
		} else {
			finish();
		}
	}

	private void setUpViews() {
		gameNameEditText = (EditText)findViewById(R.id.game_name);
		addButton = (Button)findViewById(R.id.add_button);
		viewPublicButton = (Button)findViewById(R.id.view_public_button);
		cancelButton = (Button)findViewById(R.id.cancel_button);
		
		viewPublicButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//Intent intent = new Intent(AddGameActivity.this, ViewPublicGameActivity.class);
				//startActivity(intent);

			   viewPublicGameClicked(v);	
			}
		});
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addGame();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
		gameNameEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending = true;
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			public void afterTextChanged(Editable s) { }
		});
	}

}
