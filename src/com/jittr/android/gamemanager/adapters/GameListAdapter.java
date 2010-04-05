package com.jittr.android.gamemanager.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jittr.android.gamemanager.R;
import com.jittr.android.gamemanager.games.Game;
//import com.jittr.android.gamemanager.
import com.jittr.android.gamemanager.views.GameListItem;

public class GameListAdapter extends BaseAdapter {
	
	private ArrayList<Game> games;
	private Context context;

	public GameListAdapter(Context context, ArrayList<Game> games) {
		this.games = games;
		this.context = context;
	}

	public int getCount() {
		return games.size();
	}

	public Game getItem(int position) {
		return (null == games) ? null : games.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		GameListItem tli;
		if (null == convertView) {
			tli = (GameListItem)View.inflate(context, R.layout.game_list_item, null);
		} else {
			tli = (GameListItem)convertView;
		}
		tli.setGame(games.get(position));
		return tli;
	}

	public void forceReload() {
		notifyDataSetChanged();
	}

	public void toggleGameCompleteAtPosition(int position) {
		Game game = getItem(position);
		game.toggleComplete();
		notifyDataSetChanged();
	}

	public Long[] removeCompletedGames() {
		ArrayList<Game> completedGames = new ArrayList<Game>();
		ArrayList<Long> completedIds = new ArrayList<Long>();
		for (Game game : games) {
			if (game.isComplete() ) {
				completedIds.add(game.getId());
				completedGames.add(game);
			}
		}		games.removeAll(completedGames);
		notifyDataSetChanged();
		return completedIds.toArray(new Long[]{});
	}

}
