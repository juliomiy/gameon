package com.jittr.android.foursquare;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

//import org.apache.commons.httpclient.util.HttpURLConnection;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpResponse;
import android.content.Context;

import com.jittr.android.gamemanager.GameOnProperties;

public class GOFoursquareWrapper {
	public final static String FOURSQUARE_REQUEST_TOKEN_ENDPOINT_URL="http://foursquare.com/oauth/request_token";
	public final static String FOURSQUARE_ACCESS_TOKEN_ENDPOINT_URL="http://foursquare.com/oauth/access_token";
    public final static String FOURSQUARE_AUTHORIZE_WEBSITE_URL="http://foursquare.com/oauth/authorize";
    public final static String GAMEON_FOURSQUARE_CALLBACK_URL="http://jittr.com/foursquare";
	private final static String TAG = "GOFoursquareWraper";
	//private DefaultOAuthConsumer consumer;
	private CommonsHttpOAuthConsumer consumer;
	private String consumerKey;
	private String consumerSecretKey;
	private DefaultOAuthProvider provider;
	
	private GameOnProperties gameOnProperties;
	private Context context;
	private String authUrl;

	private String accessToken;
	private String accessTokenSecret;
	
	private int lastStatusCode = -1;
	private String lastStatus;
	
	/* prepare the object - currently the Oauth tokens are hardcoded via the defaultValue in the retrieveSharedPreference call
	 * TODO - replace the defaultValue with Null.
	 */
	public GOFoursquareWrapper(Context context) {
		 this.context=context;
		 gameOnProperties = new GameOnProperties(context);
		 consumerKey = gameOnProperties.getProperty("GAMEON_FOURSQUARE_CONSUMER_KEY");   //obtain consumer gameon keys from properties
		 consumerSecretKey = gameOnProperties.getProperty("GAMEON_FOURSQUARE_CONSUMER_SECRET");
   	     consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecretKey);
         accessToken = gameOnProperties.retrieveSharedPreference("GAMEON_FOURSQUARE_OAUTH_TOKEN","AQM0W1I4W5RLVPLKD2JCM0CFOAPGJFNKLLQFDFAS4X4U1EK4");
         accessTokenSecret = gameOnProperties.retrieveSharedPreference("GAMEON_FOURSQUARE_OAUTH_TOKEN_SECRET","Z124AVQQHMPEMDHBWWDIUPFQHTYTGE3UZ2GY1HUAOUXEJGJK");
         if (isAuthorized()==true) consumer.setTokenWithSecret(accessToken, accessTokenSecret);

	} //constructor

	/* return the url to take the user to grant rights foursquare page. If user selected  "Approve" , Gameon
	 * will be given rights to act on their behalf
	 */
	public String getAuthUrl()  {
		provider = new DefaultOAuthProvider(FOURSQUARE_REQUEST_TOKEN_ENDPOINT_URL,
                FOURSQUARE_ACCESS_TOKEN_ENDPOINT_URL,
                FOURSQUARE_AUTHORIZE_WEBSITE_URL);
		try {
			authUrl = provider.retrieveRequestToken(consumer, GAMEON_FOURSQUARE_CALLBACK_URL);
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
		return authUrl;
	}  //getAuthUrl
	
	/* Return true if the user has authorized application consumer, false otherwise
	 * 
	 */
	public boolean isAuthorized() {
	    if (accessToken == null || accessTokenSecret == null)
		    return false;
	    else
	    	return true;
	}  //is Authorized
	
	public boolean venueDetailAPI(String vid) {
		String api = "http://api.foursquare.com/v1/venue?vid=" + vid;
		BasicHttpResponse response = null;
        
		HttpGet request  = signRequest(api);
        if (null == request) return false;
        
 		HttpClient httpClient = new DefaultHttpClient();
        try {
        	//httpClient.
			response =  (BasicHttpResponse) httpClient.execute(request);
			lastStatusCode = response.getStatusLine().getStatusCode();
			lastStatus = response.getStatusLine().getReasonPhrase();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (null == response ? false: true);
	}
	
	public boolean userDetailAPI() {
    	// send the request
		String api = "http://api.foursquare.com/v1/user";
		BasicHttpResponse response = null;
        
		HttpGet request  = signRequest(api);
        if (null == request) return false;
        
 		HttpClient httpClient = new DefaultHttpClient();
        try {
        	//httpClient.
			response =  (BasicHttpResponse) httpClient.execute(request);
			lastStatusCode = response.getStatusLine().getStatusCode();
			lastStatus = response.getStatusLine().getReasonPhrase();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (null == response ? false: true);
	}
 /* Checkin API */
	public boolean checkIns() {
    	// send the request
		String api = "http://api.foursquare.com/v1/checkins";
		BasicHttpResponse response = null;
        
		HttpGet request  = signRequest(api);
        if (null == request) return false;
        
 		HttpClient httpClient = new DefaultHttpClient();
        try {
        	//httpClient.
			response =  (BasicHttpResponse) httpClient.execute(request);
			lastStatusCode = response.getStatusLine().getStatusCode();
			lastStatus = response.getStatusLine().getReasonPhrase();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (null == response ? false: true);
    }  //checkins
    
	/* sign an http request with accessToken,AccessTokenSecret */
	public HttpGet signRequest(String strUrl) {
		if (isAuthorized() != true) return null;
		HttpGet request = null;
		try {
			request = new HttpGet(strUrl);
	        // sign the request
	        consumer.sign(request);
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
		return request;
	}
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

	/* Store OAuth Token/Secret in sharedPreferences */
	 public void storeAccessToken(String accessToken, String accessTokenSecret) {
		    gameOnProperties.storeSharedPreference("GAMEON_FOURSQUARE_OAUTH_TOKEN", accessToken);
		    gameOnProperties.storeSharedPreference("GAMEON_FOURSQUARE_OAUTH_TOKEN_SECRET", accessTokenSecret);
            this.accessToken= accessToken;
            this.accessTokenSecret=accessTokenSecret;
	 } //storeAccessToken

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		
	}
	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret=accessTokenSecret;
	}
	
	public int getLastStatusCode() {
        return lastStatusCode;
	}
	public String getLastStatus() {
		return lastStatus;
	}
}  //class
