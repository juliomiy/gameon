package com.jittr.android.gamemanager.games;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GamesSQLiteOpenHelper extends SQLiteOpenHelper {
	//TODO - retrieve ddl instead of having it hardcoded in the code
	//TODO - new Games ID which recognizes user defined games
	//TODO - what userID for user, phone # ?
	public static final int VERSION = 3;
	public static final String DB_NAME  = "jittr_gameOn_db.sqlite";
	public static final String GAME_TABLE = "go_games";
    public static final String GAME_USER_SETTINGS_TABLE = "go_userSettings";
	public static final String GAME_USER_TABLE = "go_user";
	public static final String GAME_ID = "id";
	public static final String GAME_NAME="name";
	public static final String GAME_COMPLETE = "complete";
	public static final String GAME_PUBLIC_PRIVATE ="visibility" ;
	public static final String GAME_TYPE = "type";
	public static final String GAME_MODIFIEDDATE ="modifiedDate";
	public static final String GAME_CREATEDDATE = "createdDate";
	public static final String GAME_BANKBALANCE = "bankBalance";
	public static final String GAME_USERNAME = "userName";
	public static final String GAME_TWITTER = "twitter";
	public static final String GAME_FACEBOOK = "facebook";
	public static final String GAME_FOURSQUARE = "foursquare";
	public static final String GAME_USER_ID = "userID";
	public static final String GAME_TWITTER_DEFAULT="twitterDefault";
	public static final String GAME_FACEBOOK_DEFAULT="facebookDefault";
    public static final String GAME_FOURSQUARE_DEFAULT="foursquareDefault";
	
	public GamesSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropAndCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We do nothing here yet
		// Later, if we change the way the database is structured, 
		// we'd increment the VERSION constant and Android would 
		// automatically call onUpgrade where we'd call different 
		// sql to modify the db here.
		dropAndCreate(db);
	}

	protected void dropAndCreate(SQLiteDatabase db) {
		dropTables(db);
		createTables(db);
	}
	
	private void dropTables(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + GAME_TABLE + ";");
		db.execSQL("Drop table if exists " + GAME_USER_TABLE + ";");
		db.execSQL("Drop table if exists " + GAME_USER_SETTINGS_TABLE + ";");
	}

	protected void createTables(SQLiteDatabase db) {
        String sql;
		db.execSQL(
				"create table " + GAME_TABLE +" (" +
				GAME_ID + " integer primary key autoincrement not null," +
				GAME_NAME + " text," +
				GAME_TYPE + " text," +
				GAME_PUBLIC_PRIVATE + " integer not null default 0," +
				GAME_COMPLETE + " text not null default 0," +
				GAME_CREATEDDATE + " timestamp not null default CURRENT_TIMESTAMP," +
				GAME_MODIFIEDDATE + " timestamp" +
				");"
			);
		sql = "create table go_user (userID integer primary key autoincrement not null," +
		      "userName text not null default 'Julio'," +
		      "bankBalance float not null default 0," +
		      "createdDate timestamp not null default CURRENT_TIMESTAMP," +
		      "modifiedDate timestamp null" +
				");";
		db.execSQL(sql);
		sql = "create table go_userSettings(userID integer primary key not null," +
                " foursquare text null," +
		        " twitter text null," +
		        " facebook text null," +
				" twitterDefault text not null default '0'," +
				" facebookDefault text not null default '0'," +
				" foursquareDefault text not null default '0'," +
				" lastSync datetime null," +
				" createdDate timestamp not null default CURRENT_TIMESTAMP," +
				" modifiedDate timestamp null " +
				");";
		db.execSQL(sql);
        setDefaultSettings(db);
	}

	/*Convenience method for development to set a record in go_user and go_userSettings */
	private void setDefaultSettings(SQLiteDatabase db) {
		//insert to go_user
		ContentValues values = new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_USERNAME, "juliomiy");
		values.put(GamesSQLiteOpenHelper.GAME_BANKBALANCE, 2000);
		long id = db.insert(GamesSQLiteOpenHelper.GAME_USER_TABLE, null, values);
		
		//insert to go_userSettings
		values=new ContentValues();
		values.put(GamesSQLiteOpenHelper.GAME_USER_ID, id);
		values.put(GamesSQLiteOpenHelper.GAME_TWITTER, "juliomiy");
		values.put(GamesSQLiteOpenHelper.GAME_FACEBOOK, "juliomiyares@mac.com");
		values.put(GamesSQLiteOpenHelper.GAME_FOURSQUARE,"9173702880");
		db.insert(GamesSQLiteOpenHelper.GAME_USER_SETTINGS_TABLE, null, values);
	} //setDefaultSettings
}
