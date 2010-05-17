package com.jittr.android.webservicexml;

import java.io.InputStreamReader;
//import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.webservicexml.BaseFeedParser;
import com.jittr.android.webservicexml.GameOnAPIs;
import com.jittr.android.webservicexml.GameOnParserType;

import com.jittr.android.webservicexml.gameon.GOPublicGamesAPI;

public class SaxFeedParser<LISTOBJECT> extends BaseFeedParser {

    private static final String TAG = "SaxFeedParser";
	private GameOnAPIs handlerType;
    private GOPublicGamesAPI handler;
    
	public SaxFeedParser(GameOnAPIs handlerType, String feedUrl) throws GOWebServiceAPIException {
		super(feedUrl);
		this.handlerType=handlerType;
	}
	public ArrayList<LISTOBJECT> parse() throws GOWebServiceAPIException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser parser = factory.newSAXParser();
		try {
			
		    handler = (GOPublicGamesAPI) GameOnParserType.getGameOnParserType(handlerType);
		    //parser.parse(stream, handler);
		    parser.parse(this.getInputStream(), handler);
		    //parser.parse(this.getInputStream(), handler);
			return  handler.getList();
		} catch (UnknownHostException e) {
			throw new GOWebServiceAPIException(SaxFeedParser.TAG,e.getMessage());
			//throw new RuntimeException(e);
		} catch (Exception e) {
			throw new GOWebServiceAPIException(SaxFeedParser.TAG,e.getMessage());
			
		} //catch
//		return null;
	} //parse
}  //class
