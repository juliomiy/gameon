package com.jittr.android.gamemanager;

import java.util.ArrayList;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.jittr.android.gamemanager.GameOnGlobalConstants.*;
import com.jittr.android.gamemanager.games.Game;
import static com.jittr.android.gamemanager.games.GamesSQLiteOpenHelper.*;
import com.jittr.android.gamemanager.games.GamesSQLiteOpenHelper;
//import com.jittr.android.webservicexml.foursquare.SaxFeedParser;
import com.jittr.android.webservicexml.GOWebServiceAPIException;
import  com.jittr.android.webservicexml.GameOnAPIs;
import com.jittr.android.webservicexml.SaxFeedParser;

public class GameManagerApplication extends Application {

	private static final String TAG = "GameManagerAppliation";
	public static Context appContext;
	private SQLiteDatabase database;
	private ArrayList<Game> currentGames;
	private ArrayList<Game> publicGames;
	private GameUserSettings userSettings;
//	private SaxFeedParser p;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
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
	/*This is an involved function as a controller - for the add game to be successful , the game needs to be added to the db on the device, the listactivity
	 * datasource that the user sees as well as the central database on the remote host. The order of risk for each of those steps least to most is
	 *      add to listactivity
	 *      add to sqlite local storage
	 *      add to remote host
	 *That is also the order from least to most time that each transaction takes to complete. Though atomicity is required , it can not be guaranteed without a lot of code * 
	 * TODO - add webservice to add game to local host
	 * Deal with RowID/GameID and uniqueness throughout*/
	public long addGame(Game game) {
		assert(null != game);
		long rowID=-1;
		
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_NAME, game.getName());
		values.put(GamesSQLiteOpenHelper.GAME_COMPLETE, Boolean.toString(game.isComplete()));
        values.put(GamesSQLiteOpenHelper.GAME_TYPE, game.getType());
        values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK,game.getFacebookNetwork());
        values.put(GamesSQLiteOpenHelper.GAME_TWITTER,game.getTwitterNetwork());
        values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE,game.getFoursquareNetwork());
        /* Save Game to local handset storage*/
		rowID = database.insert(GamesSQLiteOpenHelper.GAME_TABLE, null, values);
		if (rowID != SQLITE_INSERT_ERROR) {
            game.setId(rowID);
            long responseCode;
            if ((responseCode = GameOnMasterAPI.insertGame(game)) == GAMEON_API_SUCCESS) {
		      /* Save game to internal List*/
		        currentGames.add(game);  //Add inserted game to internal list
            } //if
		} //if
		return rowID;
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
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK,t.getFacebookNetwork());
	    values.put(GamesSQLiteOpenHelper.GAME_TWITTER,t.getTwitterNetwork());
	    values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE,t.getFoursquareNetwork());
		values.put(GamesSQLiteOpenHelper.GAME_MODIFIEDDATE, t.getModifiedDate());

		long id = t.getId();
		String where = String.format("%s = %d", GamesSQLiteOpenHelper.GAME_ID, id);

		database.update(GamesSQLiteOpenHelper.GAME_TABLE, values, where, null);
	}
	
	//TODO - uncomment the where clause
	public void saveSettings(GameUserSettings settings) {
		assert(null != settings);
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK, settings.getFacebook());
		values.put(GamesSQLiteOpenHelper.GAME_TWITTER, settings.getTwitter());
		values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE, settings.getFoursquare());
		values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE_DEFAULT, settings.isDefaultFoursquare());
		values.put(GamesSQLiteOpenHelper.GAME_TWITTER_DEFAULT, Boolean.toString( settings.isDefaultTwitter()));
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK_DEFAULT,Boolean.toString(settings.isDefaultFacebook()));
		
		values.put(GamesSQLiteOpenHelper.GAME_MODIFIEDDATE, settings.getModifiedDate());
		long id = settings.getUserID();
		String where = String.format("%s = %d", GamesSQLiteOpenHelper.GAME_USER_ID, id);

		database.update(GamesSQLiteOpenHelper.GAME_USER_SETTINGS_TABLE, values, null, null);
		
	}
	/* Should be a singleton select.
	 * assume only 1 record so the where clause is omitted
	 * TODO - treat as a singleton - change to a singleton class for the userSettings
	 */
	public GameUserSettings getGameUserSettings() {
	 //   String tableFields[]= {GamesSQLiteOpenHelper.GAME_FACEBOOK,GamesSQLiteOpenHelper.GAME_TWITTER,GamesSQLiteOpenHelper.GAME_FOURSQUARE,GamesSQLiteOpenHelper.GAME_FACEBOOK_DEFAULT, GamesSQLiteOpenHelper.GAME_TWITTER_DEFAULT, GamesSQLiteOpenHelper.GAME_FOURSQUARE_DEFAULT};
	    Cursor tasksCursor = database.query(GamesSQLiteOpenHelper.GAME_USER_SETTINGS_TABLE, new String[] {GamesSQLiteOpenHelper.GAME_FACEBOOK,GamesSQLiteOpenHelper.GAME_TWITTER,GamesSQLiteOpenHelper.GAME_FOURSQUARE,GamesSQLiteOpenHelper.GAME_FACEBOOK_DEFAULT, GamesSQLiteOpenHelper.GAME_TWITTER_DEFAULT, GamesSQLiteOpenHelper.GAME_FOURSQUARE_DEFAULT,GamesSQLiteOpenHelper.GAME_TWITTER_OAUTH_TOKEN,GamesSQLiteOpenHelper.GAME_TWITTER_OAUTH_TOKEN_SECRET},  null, null, null, null, null);
		tasksCursor.moveToFirst();
		if (! tasksCursor.isAfterLast()) {
			userSettings = new GameUserSettings();
			do {
               // String bool = tasksCursor.getString(3);
               // if (bool == "1" || bool == "true" ) userSettings.setDefaultFacebook(true);
				userSettings.setDefaultFacebook(Boolean.parseBoolean(tasksCursor.getString(3)));	
	             userSettings.setDefaultTwitter(Boolean.parseBoolean(tasksCursor.getString(4)));
	             userSettings.setDefaultFoursquare(Boolean.parseBoolean(tasksCursor.getString(5)));
	             userSettings.setTwitterOAuthToken(tasksCursor.getString(6));
	             userSettings.setTwitterOAuthTokenSecret(tasksCursor.getString(7));
	             userSettings.setFacebook(tasksCursor.getString(0));
                 userSettings.setTwitter(tasksCursor.getString(1));
                 userSettings.setFoursquare(tasksCursor.getString(2));
    		} while (tasksCursor.moveToNext());
		} //if
		tasksCursor.close();
		return userSettings;
	
	}  //GameUserSettings
	
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
/* Calls webservice to retrieve public Games
 * Input parameter used to select by Type- if empty , returns all
 * The return is in XML which is parsed and stored in Game objects of Game Class and in ArrayList
 * TODO - add selection parameters to date , type etc
 * TODO - url Encode the parameter(s) - some confusion around right way to do this within java
 * 
 */
	private void loadPublicGames(String sport) {
		
		String urlString = (sport != null) ? "http://jittr.com/jittr/gameon/go_getpublicgames.php?"+sport : "http://jittr.com/jittr/go_games.php ";
		
//		SaxFeedParser parser = new SaxFeedParser(GameOnAPIs.GO_PUBLIC_GAMES,urlString);
		SaxFeedParser parser;
		try {
			parser = new SaxFeedParser<Game>(GameOnAPIs.GO_PUBLIC_GAMES,urlString);
			publicGames = parser.parse();
		} catch (GOWebServiceAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		/*publicGames = new ArrayList<Game>();
		Game t = new Game("Test using loadPublic Games");
		t.setId(5);
		t.setName("Test using loadPublic Game");
	    publicGames.add(t);	*/
	}
	private void loadPublicGames() {
         loadPublicGames(null);		
	}
}
