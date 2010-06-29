package com.jittr.android.gamemanager;

import com.jittr.android.twitter.GOTwitterWrapper;
import com.jittr.android.twitter.GameOnTwitterException;
import com.jittr.android.R;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GameOnAuthorizationActivity extends Activity {
	
	  private WebView webView;
      private String authUrl;
      private GOTwitterWrapper twitter;
      private Context appContext;

	  private WebViewClient webViewClient = new WebViewClient() {
	    @Override
	    public void onLoadResource(WebView view, String url) {
	      // the URL we're looking for looks like this:
	      // http://otweet.com/authenticated?oauth_token=1234567890qwertyuiop
	      Uri uri = Uri.parse(url);
	      if (uri.getHost().equals("jittr.com")) {
	        String token = uri.getQueryParameter("oauth_token");
	        if (null != token) {
	          webView.setVisibility(View.INVISIBLE);
	          try {
				 twitter.authorize(token);
				 GameOnProperties properties  = new GameOnProperties(GameManagerApplication.appContext);   
	             properties.setFirstRun();
			  } catch (GameOnTwitterException e) {

			  }  //try-catch
			  finally {
				  finish();
			  } //finally
	        } else {
	          // tell user to try again 
	             finish();
	        } //if
	      } else {
	        super.onLoadResource(view, url);
	      }  //else
	    } //if
	   };  //onLoadResource

	   
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //app = (OTweetApplication)getApplication();
        twitter = new GOTwitterWrapper(this.getApplicationContext());
	    setContentView(R.layout.authorization_view);
	    setUpViews();
	  }
	  
	  @Override
	  protected void onResume() {
	    super.onResume();
	    String authURL = null;
		try {
			authURL = twitter.getAuthUrl();
		} catch (GameOnTwitterException e) {
/* TODO - finish is for convenience at moment */
			finish();
			return;
		}  //try block
	    webView.loadUrl(authURL);
	  }  //onResume
	  
	  private void setUpViews() {
	    webView = (WebView)findViewById(R.id.web_view);
	    webView.setWebViewClient(webViewClient);
	  }
}
