package com.jittr.android.gamemanager.views;

import com.jittr.android.gamemanager.games.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class GameListItem extends LinearLayout {
	
	private Game game;
	private CheckedTextView checkbox;

	public GameListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		checkbox = (CheckedTextView)findViewById(android.R.id.text1);
	}

	public void setGame(Game game) {
		this.game = game;
		checkbox.setText(game.getName());
		checkbox.setChecked(game.isComplete());
	}  //setGame

	public Game getGame() {
		return game;
	}

}
