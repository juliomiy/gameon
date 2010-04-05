package com.jittr.android.webservicexml;

import com.jittr.android.webservicexml.GameOnAPIs;
import com.jittr.android.webservicexml.foursquare.FQUserAPI;
import com.jittr.android.webservicexml.foursquare.FourSquareHandler;
import com.jittr.android.webservicexml.gameon.GOPublicGamesAPI;

public abstract class GameOnParserType {

	public  static GameOnHandler getGameOnParserType(GameOnAPIs handlerType) {
		switch (handlerType){
		   case FS_FRIENDS:
		//	  return new FQFriendsAPI();
		  case FS_USER:
	//	      return new FQUserAPI();
		      
		  case GO_PUBLIC_GAMES:
		       return new GOPublicGamesAPI();
		//case ANDROID_SAX:
		//	return new AndroidSaxFeedParser(feedUrl);
		//case XML_PULL:
		//	return new XmlPullFeedParser(feedUrl);
		   default: return null;
	    } //switch
	}  //constructor
} //class