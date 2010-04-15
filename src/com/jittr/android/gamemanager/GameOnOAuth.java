package com.jittr.android.gamemanager;

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
}   //Class GameOnAuth
