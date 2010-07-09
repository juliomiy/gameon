package com.jittr.android.gamemanager;

import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import com.jittr.android.R;
import com.jittr.android.foursquare.GOFoursquareWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class GameOnAuthorizationActivity2 extends Activity {
	  private WebView webView;
      private String authURL;
      private GOFoursquareWrapper foursquare;
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
   	        }  //if   	     
  	      }  //if
	      super.onLoadResource(view, url);
  	    } //if
  	   };  //onLoadResource

  	   
  	  @Override
  	  protected void onCreate(Bundle savedInstanceState) {
  	    super.onCreate(savedInstanceState);
  	    appContext = this.getApplicationContext();
        foursquare = new GOFoursquareWrapper(appContext);
  	    setContentView(R.layout.authorization_view);
  	    setUpViews();
  	  }
  	  
  	  /* get the authorization url for foursquare Oauth and fire off webview
  	   * (non-Javadoc)
  	   * @see android.app.Activity#onResume()
  	   */
  	  @Override
  	  protected void onResume() {
  	    super.onResume();
  	    authURL = null;
  	//	try {
  			authURL = foursquare.getAuthUrl();
  	//	} catch (GameOnTwitterException e) {
  /* TODO - finish is for convenience at moment */
  	//		finish();
  		//	return;
  	//	}  //try block
  	    webView.loadUrl(authURL);
  	  }  //onResume
  	 
  	  /* setup WebView which will load the foursquare oauth approval web flow
  	   * Need to enable javascript as FS forms within emulator complain without it
  	   */
  	  private void setUpViews() {
  	    webView = (WebView)findViewById(R.id.web_view);
  	    webView.setWebViewClient(webViewClient);
  	    webView.getSettings().setJavaScriptEnabled(true);
  	   }
}
