package com.jittr.android.foursquare;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.content.Context;

import com.jittr.android.gamemanager.GameOnProperties;

public class GOFoursquareWrapper {
	public final static String FOURSQUARE_REQUEST_TOKEN_ENDPOINT_URL="http://foursquare.com/oauth/request_token";
	public final static String FOURSQUARE_ACCESS_TOKEN_ENDPOINT_URL="http://foursquare.com/oauth/access_token";
    public final static String FOURSQUARE_AUTHORIZE_WEBSITE_URL="http://foursquare.com/oauth/authorize";
	private final static String TAG = "GOFoursquareWraper";
	private DefaultOAuthConsumer consumer;
	private String consumerKey;
	private String consumerSecretKey;
	private DefaultOAuthProvider provider;
	private RequestToken currentRequestToken;
	private AccessToken accessToken;
	
	private GameOnProperties gameOnProperties;
	private Context context;
	private String authUrl;

	public GOFoursquareWrapper(Context context) {
		 this.context=context;
		 gameOnProperties = new GameOnProperties(context);
		 consumerKey = gameOnProperties.getProperty("GAMEON_FOURSQUARE_CONSUMER_KEY");   //obtain consumer gameon keys from properties
		 consumerSecretKey = gameOnProperties.getProperty("GAMEON_FOURSQUARE_CONSUMER_SECRET");
   	     consumer = new DefaultOAuthConsumer(consumerKey,consumerSecretKey);
//		 accessToken = loadAccessToken();
		 
		// if (accessToken != null) {
	//		 twitter.setOAuthAccessToken(accessToken.getToken(),accessToken.getTokenSecret());
		// }
	} //constructor

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecretKey() {
		return consumerSecretKey;
	}

	public void setConsumerSecretKey(String consumerSecretKey) {
		this.consumerSecretKey = consumerSecretKey;
	}
}
