package com.jittr.android.webservicexml.foursquare;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.webservicexml.BaseFeedParser;
import com.jittr.android.webservicexml.GameOnAPIs;
import com.jittr.android.webservicexml.GameOnParserType;

import com.jittr.android.webservicexml.gameon.GOPublicGamesAPI;

public class SaxFeedParser extends BaseFeedParser {

    private GameOnAPIs handlerType;
    private GOPublicGamesAPI handler;
    
	public SaxFeedParser(GameOnAPIs handlerType, String feedUrl) {
		super(feedUrl);
		this.handlerType=handlerType;
	}
	public ArrayList<Game> parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			//FQFriendsAPI handler = new FQFriendsAPI();
			//FourSquareHandler handler = new FQFriendsAPI();
		    handler = (GOPublicGamesAPI) GameOnParserType.getGameOnParserType(handlerType);
			
			parser.parse(this.getInputStream(), handler);
			return handler.getGames();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  //catch
	} //parse
}  //class
