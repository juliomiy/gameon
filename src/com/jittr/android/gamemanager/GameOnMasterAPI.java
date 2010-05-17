package com.jittr.android.gamemanager;

import static com.jittr.android.gamemanager.GameOnGlobalConstants.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.jittr.android.gamemanager.games.Game;

public class GameOnMasterAPI {

	
	/* Insert game into host storage - master table via webservice API - 
	 * TODO - on the host side - change from GET to POST
	 * TODO - both on host and in method , add remaining query parameters
	 */
	public static long insertGame(Game game) {
		   String responseMessage;
		   StringBuffer urlString = new StringBuffer();
		   int responseCode = -1;
		   
		   urlString.append("gameid="+game.getId());
		   urlString.append("&name="+game.getName());
		   urlString.append("&eventname="+game.getEventName());
			try {
				
				String urlEncoded = "http://jittr.com/jittr/go_postnewgame.php?" + urlString.toString().replace(" ", "%20");
				URL url = new URL(urlEncoded);
				
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setReadTimeout(0);
				String ss = connection.getContentEncoding();
				BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()));
				while ((responseMessage =  br.readLine()) != null) {
					//System.out.println(s);
				}
				responseCode = connection.getResponseCode();
				responseMessage = connection.getResponseMessage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return ((responseCode == 200) ? GAMEON_API_SUCCESS : GAMEON_API_FAULRE);
		
	}

}
