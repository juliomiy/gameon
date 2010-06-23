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
		 if (accessToken != null) {
			 twitter.setOAuthAccessToken(accessToken.getToken(),accessToken.getTokenSecret());
		 }
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
	public String getAuthUrl() {
		 try {
		      if (null == currentRequestToken) {
		        currentRequestToken = twitter.getOAuthRequestToken();
		      }
		      authUrl = currentRequestToken.getAuthorizationURL();
		    } catch (TwitterException e) {
		      e.printStackTrace();
		 }  //try
	   return authUrl;	
	} //authorize

	 public boolean authorize(String pin) {
		    try {
		      AccessToken accessToken = twitter.getOAuthAccessToken();
		      storeAccessToken(accessToken);
		      return true;
		    } catch (TwitterException e) {
		      throw new RuntimeException(e.getMessage() + " Unable to authorize user", e); 
		    }
		  }
 	/* Store OAuth Token/Secret in sharedPreferences */
 	 public void storeAccessToken(AccessToken accessToken) {
		    gameOnProperties.storeSharedPreference("GAMEON_TWITTER_OAUTH_TOKEN", accessToken.getToken());
		    gameOnProperties.storeSharedPreference("GAMEON_TWITTER_OAUTH_SECRET_TOKEN", accessToken.getTokenSecret());
		    this.accessToken = accessToken;
     } //storeAccessToken

	 public void sendTwitterUpdate(String status) {
            try {
				twitter.updateStatus(status);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
} //class
