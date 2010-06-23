package com.jittr.android.webservice;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import static com.jittr.android.gamemanager.GameOnOAuth.*;
import android.content.Context;

import com.jittr.android.gamemanager.GameManagerApplication;
import com.jittr.android.gamemanager.GameOnProperties;

public class TwitterAPIs {
	private String consumerKey, consumerSecret;
	private GameOnProperties properties;
	private Context context;
	
/* Update Twitter Status using Oauth for authenticaion
 * 
 */
	public TwitterAPIs(Context context) {
		this.context=context;
	}
	public TwitterAPIs() {
		this(GameManagerApplication.appContext);
	}
	public void sendTwitterUpdate(String oAuthToken, String oAuthTokenSecret, String statusUpdate) {
		// TODO Auto-generated method stub
		TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        
        if (consumerKey == null || consumerSecret==null) {
            properties = new GameOnProperties(context);
            consumerKey = properties.getProperty("GAMEON_TWITTER_CONSUMER_KEY");
            consumerSecret = properties.getProperty("GAMEON_TWITTER_CONSUMER_SECRET");
        } //if
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken accessToken = new AccessToken(oAuthToken,oAuthTokenSecret); 
        twitter.setOAuthAccessToken(accessToken);
        Status status;
		try {
			status = twitter.updateStatus(statusUpdate);
			System.out.println("Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //try
        
     }

	public void sendTwitterUpdate(String statusUpdate) {
	}
}   //class
