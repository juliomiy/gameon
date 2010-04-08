package com.jittr.android.gamemanager;

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
		Game g = adapter.getItem(position);
		//title = (Address) g.getName();
		if (null != g.getName()) {
			title = g;
			//title.setAddressLine(0,g.getName());
			
			Intent intent = new Intent();
			intent.putExtra(PUBLIC_GAME_RESULT,title);
			setResult(RESULT_OK, intent);
		}
		
		finish();
//		app.saveGame(g);
	}

	protected void cancel() {
        finish();		
	}

}
