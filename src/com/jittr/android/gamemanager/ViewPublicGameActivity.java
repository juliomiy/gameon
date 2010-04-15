package com.jittr.android.gamemanager;

import static com.jittr.android.gamemanager.ViewPublicGameActivity.CUSTOMIZE_GAME;

import com.jittr.android.gamemanager.adapters.GameListAdapter;
import com.jittr.android.gamemanager.games.Game;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewPublicGameActivity extends ListActivity {
	public static final String PUBLIC_GAME_RESULT = "public_name";
	public static final String CUSTOMIZE_GAME = "customize_game";
	private Intent callingIntent;
	private GameListAdapter adapter;
	private GameManagerApplication app;
	private Button cancelButton;
	private Game title;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_public_file);
        setUpViews();
        app = (GameManagerApplication)getApplication();
        adapter = new GameListAdapter(this, app.getPublicGames());
        setListAdapter(adapter);
    }
	
	@Override
	protected void onResume() {
	   super.onResume();
	   callingIntent = super.getIntent();
       adapter.forceReload();
	}

	private void setUpViews() {
		cancelButton = (Button)findViewById(R.id.cancel_button);
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});

	}  //setupviews

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
//		adapter.toggleGameCompleteAtPosition(position);
		Game game = adapter.getItem(position);
	
		if (null != game) {
			//Intent intent = new Intent(ViewPublicGameActivity.this, CustomizePublicGameActivity.class);
			callingIntent.putExtra(PUBLIC_GAME_RESULT,game);
			//startActivity(intent);
			//startActivityForResult(intent,REQUEST_CHOOSE_PUBLIC_GAME);			
	//		Intent intent = new Intent();
			setResult(RESULT_OK, callingIntent);
		}
		
		finish();
//		app.saveGame(g);
	}

	protected void cancel() {
		setResult(RESULT_CANCELED,callingIntent);
        finish();		
	}

}
