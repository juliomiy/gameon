package com.jittr.android.gamemanager;

import android.app.Activity;

public class GameOnActivity extends Activity {

	public static GameManagerApplication appObject;
	
	/*static {
		appObject = (GameManagerApplication) getApplication();
		
	}
	*/
	public GameOnActivity() {
		super();
	}

	protected GameManagerApplication getStuffApplication()
	{
		return (GameManagerApplication)getApplication();
	}

}