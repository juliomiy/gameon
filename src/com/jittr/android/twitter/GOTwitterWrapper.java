package com.jittr.android.twitter;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.jittr.android.gamemanager.GameOnProperties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

public class GOTwitterWrapper {
	private static final String TAG = "GOTwitterWraper";
	private Twitter twitter;
	private RequestToken currentRequestToken;
	private AccessToken accessToken;
	private String consumerKey;
	private String consumerSecretKey;
	private GameOnProperties gameOnProperties;
	private Context context;
	private String authUrl;
	
	public GOTwitterWrapper(Context context) {
		 this.context=context;
		 twitter = new TwitterFactory().getInstance();   //instantiate new twitter object
		 gameOnProperties = new GameOnProperties(context);
		 consumerKey = gameOnProperties.getProperty("GAMEON_TWITTER_CONSUMER_KEY");   //obtain consumer gameon keys from properties
		 consumerSecretKey = gameOnProperties.getProperty("GAMEON_TWITTER_CONSUMER_SECRET");
		 twitter.setOAuthConsumer(consumerKey, consumerSecretKey);
		 accessToken = loadAccessToken();
		 
		// if (accessToken != null) {
	//		 twitter.setOAuthAccessToken(accessToken.getToken(),accessToken.getTokenSecret());
		// }
	} //constructor
	
	private AccessToken loadAccessToken() {
	    String token = gameOnProperties.retrieveSharedPreference("GAMEON_TWITTER_OAUTH_TOKEN"); 
	    String tokenSecret = gameOnProperties.retrieveSharedPreference("GAMEON_TWITTER_OAUTH_SECRET_TOKEN");
	    if (null != token && null != tokenSecret) {
	      return new AccessToken(token, tokenSecret);
	    } else {
	      return null;
	    }
	  }
    /* Get Request URL to get requestToken */
	public String getAuthUrl() throws GameOnTwitterException {
        authUrl = null;
		try {
		      if (null == currentRequestToken) {
		        currentRequestToken = twitter.getOAuthRequestToken();
		      }
		      
		      authUrl = currentRequestToken.getAuthorizationURL();
		    } catch (TwitterException e) {
		    	e.printStackTrace();
		    	throw new GameOnTwitterException("Error obtaining Request Token", e.getMessage(), e, TAG);
		 }  //try
	   return authUrl;	
	} //authorize

	 public boolean authorize(String pin) throws GameOnTwitterException {
		    try {
		      AccessToken accessToken = twitter.getOAuthAccessToken();
		      storeAccessToken(accessToken);
		      return true;
		    } catch (TwitterException e) {
		      throw new GameOnTwitterException( "Unable to authorize user",e.getMessage(), e, TAG); 
		    }
		  }
 	/* Store OAuth Token/Secret in sharedPreferences */
 	 public void storeAccessToken(AccessToken accessToken) {
		    gameOnProperties.storeSharedPreference("GAMEON_TWITTER_OAUTH_TOKEN", accessToken.getToken());
		    gameOnProperties.storeSharedPreference("GAMEON_TWITTER_OAUTH_SECRET_TOKEN", accessToken.getTokenSecret());
		    this.accessToken = accessToken;
     } //storeAccessToken

 	 /*update this user's status
 	  * 
 	  */
	 public void sendTwitterUpdate(String status) throws GameOnTwitterException {
            try {
				twitter.updateStatus(status);
			} catch (TwitterException e) {
				throw new GameOnTwitterException("Failed Status Update",e.getMessage() ,e, TAG);
			}
	  }  //sendTwitterUpdate
	 
	 public void getMe() {
		// twitter.verifyCredentials();
	 }
} //class
