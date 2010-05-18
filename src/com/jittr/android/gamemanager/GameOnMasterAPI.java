package com.jittr.android.gamemanager;

import static com.jittr.android.gamemanager.GameOnGlobalConstants.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

import com.jittr.android.gamemanager.games.Game;

public class GameOnMasterAPI {

	
	/* Insert game into host storage - master table via webservice API - 
	 * TODO - on the host side - change from GET to POST
	 * TODO - both on host and in method , add remaining query parameters
     * TODO - flesh out Finally to release connection
     * TODO - add correct exception catches
	 */
	public static long insertGame(Game game) {
		   String responseMessage=null;
		   StringBuffer urlString = new StringBuffer();
		   HttpClient client=null;
		   int responseCode = -1;
		   
		   urlString.append("gameid="+game.getId());
		   urlString.append("&title="+game.getName());
		   urlString.append("&eventname="+game.getEventName());
		   urlString.append("&createdbyusername="+game.getCreatedByUserName());
		   urlString.append("&createdbyuserid="+game.getCreatedByUserID());
		   urlString.append("&wagertype=1");
		   try {
			    HttpResponse responseHeader = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK, "OK");
				client = new DefaultHttpClient(); 
				String urlEncoded = "http://jittr.com/jittr/gameon/go_postnewgame.php?" + urlString.toString().replace(" ", "%20");
				HttpGet get = new HttpGet(urlEncoded);
                ResponseHandler<String> response=new BasicResponseHandler();
				responseMessage = client.execute(get,response);
				responseCode = responseHeader.getStatusLine().getStatusCode();
            } catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (client != null) {
					client.getConnectionManager().shutdown();
				} //if
			} //finally
			
		return ((responseCode == 200) ? GAMEON_API_SUCCESS : GAMEON_API_FAULRE);
		
	}

}
