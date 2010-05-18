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
import com.jittr.android.gamemanager.GameOnGlobalConstants;

/* Add Game Activity - can either choose a public game or define your own game. 
 * Customization activity is invoked for both before saving 
 */
public class AddGameActivity extends GameOnActivity {
	private EditText gameNameEditText;
	private Button addButton;
	private Button cancelButton;
	private Button viewPublicButton;
	private boolean changesPending;
	private AlertDialog unsavedChangesDialog;
	private GameUserSettings userSettings;
	private Game game;
    private Game publicGame;
    private static final String TAG="AddGameActivity";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game);
        setUpViews();
        /*TODO instantiate userSettings Object */
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
	
	/* Add Game to persistent data stores - this is called after creation and customization of the Game */
	protected void addGame(Game game) {
	/* Temporary code */
		if (game == null) {
		   game = new Game();
		   game.setName(gameNameEditText.getText().toString());
		   game.setCreatedByUserName("temp");
		   game.setCreatedByID(1);
		   game.setWagerType(1);
		   //customizeGame(GameOnGlobalConstants.CUSTOMIZE_USERDEFINED_GAME,game);
		 }
		/* End temporary code */
		getStuffApplication().addGame(game);
		if (game.getTwitterNetwork() != null) {
			userSettings = getStuffApplication().getGameUserSettings();
			TwitterAPIs twitter = new TwitterAPIs();
			String statusUpdate= game.getName();
			twitter.sendTwitterUpdate( userSettings.getTwitterOAuthToken(),userSettings.getTwitterOAuthTokenSecret(),statusUpdate);
		}
		finish();
	}

	/* Fire up the customize screen - if user customizes and commits, returns true and the game is saved. If the user chooses cancel
	 * game is not save. This function solely fires the appropriate intent. The onActivityResult will determine further action based on 
	 * user selection  */
	private void customizeGame(int activityType,Game game) {
		Intent intent = new Intent(AddGameActivity.this, CustomizePublicGameActivity.class);
  		intent.putExtra(CUSTOMIZE_GAME,game);
  		startActivityForResult(intent,activityType);
	}
	/* invoke webservice to display list of public games */
	public void viewPublicGameClicked(View view) {
		Intent intent = new Intent(AddGameActivity.this, ViewPublicGameActivity.class);
		startActivityForResult(intent,GameOnGlobalConstants.REQUEST_CHOOSE_PUBLIC_GAME);
	} //viewPublicGameActivity

	/* process return from invoking Select public game or customization of game
	 */
	@Override
	public void onActivityResult(int requestCode,int resultCode, Intent data) {

		if (GameOnGlobalConstants.REQUEST_CHOOSE_PUBLIC_GAME == requestCode && RESULT_OK == resultCode) {
			publicGame = data.getParcelableExtra(ViewPublicGameActivity.PUBLIC_GAME_RESULT);
			/* allow user to customize the game, choose who they are backing */
            if (publicGame != null) {
        		Intent intent = new Intent(AddGameActivity.this, CustomizePublicGameActivity.class);
        		intent.putExtra(CUSTOMIZE_GAME,publicGame);
        		startActivityForResult(intent,GameOnGlobalConstants.CUSTOMIZE_PUBLIC_GAME);
            }
		} else if (GameOnGlobalConstants.CUSTOMIZE_PUBLIC_GAME == requestCode && RESULT_OK == resultCode) {
			    publicGame = data.getParcelableExtra(CustomizePublicGameActivity.CUSTOMIZE_GAME);
		} else if (GameOnGlobalConstants.CUSTOMIZE_USERDEFINED_GAME == requestCode && RESULT_OK == resultCode) {
		        game = data.getParcelableExtra(CustomizePublicGameActivity.CUSTOMIZE_GAME);
		        addGame(game);
		} else    { 
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
						addGame(null);
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
				   viewPublicGameClicked(v);	
			}
		});
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				game = new Game(gameNameEditText.getText().toString());
			    customizeGame(GameOnGlobalConstants.CUSTOMIZE_USERDEFINED_GAME,game);
				//addGame(null);
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
