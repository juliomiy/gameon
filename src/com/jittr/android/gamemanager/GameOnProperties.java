package com.jittr.android.gamemanager;
/* Class that manages the application & user properties/preferences
 * 
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.http.AccessToken;
import com.jittr.android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import static com.jittr.android.gamemanager.GameManagerApplication.appContext;
public class GameOnProperties  {

	private static final String APPLICATION_PREFERENCES = "app_prefs";
	private Properties properties;
    private Resources resources; // = appContext.getResources();
    private Context context;
    private SharedPreferences prefs;
    
    //private AssetManager assetManager = new AssetManager();
    
	public GameOnProperties(Context context) {
		// Read from the /res/raw directory
		this.context=context;
		prefs = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
		try {
			resources = context.getResources();
		    final InputStream rawResource =  resources.openRawResource(R.raw.gameonv1);
		    properties = new Properties();
		    properties.load(rawResource);
		} catch (final NotFoundException e) {
		    System.err.println("Did not find raw resource: "+e);
		} catch (final IOException e) {
		    System.err.println("Failed to open microlog property file");
		}  //catch
	} //constructor

	/* Store a key/value pair in shared perferences*/
	public void storeSharedPreference(String key, String value) {
		if (prefs == null) return;
	    Editor editor = prefs.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	/* retrieve shared Preference value based on key */
	public String retrieveSharedPreference(String key) {
		 if (prefs == null) return null;
		 String value = prefs.getString(key, null);
         return value;
	}
	public String getProperty(final String key) {
        String value = null;
        value = properties.getProperty(key);
		return value;
	}  //getProperty

/* Checks if this application has been previously run, returns true if it has, false otherwise */	
	public boolean firstRun() {
		 if (prefs == null) return false;
		 boolean value = prefs.getBoolean("firstrun", true);
         return value;
	}
/*Update Shared Preferences to indicate first run has occurred */
	public void setFirstRun() {
		if (prefs == null) return;
	    Editor editor = prefs.edit();
	    editor.putBoolean("firstrun", true);
	    editor.commit();
	}
}  //class	
