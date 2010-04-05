package com.jittr.android.gamemanager;

import java.util.ArrayList;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.gamemanager.games.GamesSQLiteOpenHelper;
import com.jittr.android.webservicexml.foursquare.SaxFeedParser;
import  com.jittr.android.webservicexml.GameOnAPIs;

public class GameManagerApplication extends Application {

	private SQLiteDatabase database;
	private ArrayList<Game> currentGames;
	private ArrayList<Game> publicGames;
//	private SaxFeedParser p;
	
	@Override
	public void onCreate() {
		super.onCreate();
		GamesSQLiteOpenHelper helper = new GamesSQLiteOpenHelper(this);
		database = helper.getWritableDatabase();
		if (null == currentGames) {
			loadGames();
		}
	}

	@Override
	public void onTerminate() {
		database.close();
		super.onTerminate();
	}

	public void setCurrentGames(ArrayList<Game> currentGames) {
		this.currentGames = currentGames;
	}

	public ArrayList<Game> getCurrentGames() {
		return currentGames;
	}
	public void setPublicGames(ArrayList<Game> publicGames) {
		this.publicGames = publicGames;
	}

	public ArrayList<Game> getPublicGames() {
		if (publicGames == null)
			this.loadPublicGames();
		return this.publicGames;
	}
	
	/* Add Game to Database and to list */
	/*TODO - deal with possible error situation instead of assuming the insert was ok and the new Game ID returned
	 * 
	 */
	public void addGame(Game t) {
		assert(null != t);
		
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_NAME, t.getName());
		values.put(GamesSQLiteOpenHelper.GAME_COMPLETE, Boolean.toString(t.isComplete()));
        values.put(GamesSQLiteOpenHelper.GAME_TYPE, t.getType());
		t.setId(database.insert(GamesSQLiteOpenHelper.GAME_TABLE, null, values));

		currentGames.add(t);  //Add inserted game to internal list
	}
	
	/* Table go_game
	 * id, name, visibilitiy, createdDate, modifiedDate
	 * TODO - method to calculate TIMESTAMP formatted value for modifiedDate 
	 */
	public void saveGame(Game t) {
		assert(null != t);
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_NAME, t.getName());
		values.put(GamesSQLiteOpenHelper.GAME_PUBLIC_PRIVATE, Integer.toString(t.getVisibility()));
		values.put(GamesSQLiteOpenHelper.GAME_TYPE, t.getType());
		values.put(GamesSQLiteOpenHelper.GAME_MODIFIEDDATE, t.getModifiedDate());

		long id = t.getId();
		String where = String.format("%s = %d", GamesSQLiteOpenHelper.GAME_ID, id);

		database.update(GamesSQLiteOpenHelper.GAME_TABLE, values, where, null);
	}
	
	public void saveSettings(GameUserSettings settings) {
		assert(null != settings);
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK, settings.getFacebook());
		values.put(GamesSQLiteOpenHelper.GAME_TWITTER, settings.getTwitter());
		values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE, settings.getFoursquare());
		values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE_DEFAULT, settings.isDefaultFoursquare());
		values.put(GamesSQLiteOpenHelper.GAME_TWITTER_DEFAULT, settings.isDefaultTwitter());
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK_DEFAULT, settings.isDefaultFacebook());
		
		values.put(GamesSQLiteOpenHelper.GAME_MODIFIEDDATE, settings.getModifiedDate());
		long id = settings.getUserID();
		String where = String.format("%s = %d", GamesSQLiteOpenHelper.GAME_USER_ID, id);

		database.update(GamesSQLiteOpenHelper.GAME_USER_SETTINGS_TABLE, values, where, null);
		
	}
	public void deleteGames(Long[] ids) {
		StringBuffer idList = new StringBuffer();
		for (int i=0; i<ids.length; i++) {
			idList.append(ids[i]);
			if (i < ids.length - 1) {
				idList.append(",");
			}
		}
		String where = String.format("%s in (%s)", GamesSQLiteOpenHelper.GAME_ID, idList);
		database.delete(GamesSQLiteOpenHelper.GAME_TABLE, where, null);
	}

	private void loadGames() {
		currentGames = new ArrayList<Game>();
		Cursor tasksCursor = database.query(GamesSQLiteOpenHelper.GAME_TABLE, new String[] {GamesSQLiteOpenHelper.GAME_ID, GamesSQLiteOpenHelper.GAME_NAME, GamesSQLiteOpenHelper.GAME_COMPLETE}, null, null, null, null, String.format("%s,%s", GamesSQLiteOpenHelper.GAME_COMPLETE, GamesSQLiteOpenHelper.GAME_NAME));
		tasksCursor.moveToFirst();
		Game t;
		if (! tasksCursor.isAfterLast()) {
			do {
				int id = tasksCursor.getInt(0);
				String name  = tasksCursor.getString(1);
				String boolValue = tasksCursor.getString(2);
				boolean complete = Boolean.parseBoolean(boolValue); //   (boolValue);
				t = new Game(name);
				t.setId(id);
				//t.setVisibility(visibility);
				t.setComplete(complete);
				currentGames.add(t);
			} while (tasksCursor.moveToNext());
		}
		
		tasksCursor.close();
	}  //loadGames

	private void loadPublicGames() {
		SaxFeedParser parser = new SaxFeedParser(GameOnAPIs.GO_PUBLIC_GAMES,"http://juliomiyares.com/go_games.php");
		publicGames = parser.parse();
		/*publicGames = new ArrayList<Game>();
		Game t = new Game("Test using loadPublic Games");
		t.setId(5);
		t.setName("Test using loadPublic Game");
	    publicGames.add(t);	*/
	}
}
