package com.jittr.android.gamemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jittr.android.gamemanager.GameManagerApplication;
import com.jittr.android.gamemanager.GameUserSettings;
import android.content.Intent;
import android.net.Uri;
import oauth.signpost.*;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class GameOnOAuth {
    public final static String GAMEON_CONSUMER_KEY="ulBahZRGnn584YAgGMIQ";
    public final static String GAMEON_CONSUMER_SECRET="a5uugdaRSrXrYlBmb6AbR09ezfsPF34SqI3akvIVh4";
	public final static String TWITTER_REQUEST_TOKEN_ENDPOINT_URL="http://twitter.com/oauth/request_token";
	public final static String TWITTER_ACCESS_TOKEN_ENDPOINT_URL="http://twitter.com/oauth/access_token";
    public final static String TWITTER_AUTHORIZE_WEBSITE_URL="http://twitter.com/oauth/authorize";
	private static final String GAMEON_CALLBACK_URL = "http://jittr.com/jittr/confirm.php";
 
    private OAuthProvider provider;
    private OAuthConsumer consumer;
    private String providerUrl;
    private GameUserSettings userSettings;
    
 // create a consumer object and configure it with the access
    // token and token secret obtained from the service provider
    
   
    public void Step1() {
    	 consumer = new DefaultOAuthConsumer(GAMEON_CONSUMER_KEY,GAMEON_CONSUMER_SECRET);
    // create a new service provider object and configure it with
    // the URLs which provide request tokens, access tokens, and
    // the URL to which users are sent in order to grant permission
    // to your application to access protected resources
         provider = new DefaultOAuthProvider(
                TWITTER_REQUEST_TOKEN_ENDPOINT_URL, TWITTER_ACCESS_TOKEN_ENDPOINT_URL,
                TWITTER_AUTHORIZE_WEBSITE_URL);
         // fetches a request token from the service provider and builds
         // a url based on AUTHORIZE_WEBSITE_URL and CALLBACK_URL to
         // which your app must now send the user
         try {
			providerUrl = provider.retrieveRequestToken(consumer, GAMEON_CALLBACK_URL);
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	} //

	public void sendTwitterUpdate(String twitterStatusUpdate, String OAuthToken, String OAuthTokenSecret) {
		 consumer = new DefaultOAuthConsumer(GAMEON_CONSUMER_KEY,GAMEON_CONSUMER_SECRET);
		 consumer.setTokenWithSecret(OAuthToken, OAuthTokenSecret);
		// create a request that requires authentication
	        URL url;
	        //int sc;
			try {
				url = new URL("http://twitter.com/statuses/mentions.xml");
				//url = new URL("http://api.twitter.com/1/statuses/update.xml");
				
				HttpURLConnection request = (HttpURLConnection) url.openConnection();
				request.setRequestMethod("GET");
				
				//request.setRequestMethod("POST");
				request.setDoOutput(true);
				request.setDoInput(true);
				//BufferedReader br = new BufferedReader(request.getInputStream());
			
  			//request.addRequestProperty("status", "firstandroidjittrstatus");
				//request.
				 // sign the request
		        consumer.sign(request);
		        // send the request
		        request.connect();
		    
		      //  BufferedReader br = new BufferedReader(new InputStreamReader(request.));
		      
		    	//InputStream i = request.getInputStream();
		        // response status should be 200 OK
		        int sc = request.getResponseCode();
                if (sc == 200) {
                    String rm = request.getResponseMessage();
                	System.out.println("Succeed on cal l" + sc);
                	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
                	String in;
                	System.out.println("test");
                	//while ((in = br.readLine()) != null) {
                	//	System.out.print(in);
                	//}  //while
                } else {
                	System.out.println("failure " + sc );
                }  //if
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthMessageSignerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}   //Class GameOnAuth
