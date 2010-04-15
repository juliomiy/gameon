package com.jittr.android.webservicexml.gameon;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.webservicexml.GameOnHandler;

public final class GOPublicGamesAPI extends GameOnHandler {

	protected ArrayList<Game> games;
	protected Game currentGame;
	
	public ArrayList<Game> getGames() {
		return this.games;
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentGame != null) {
			if (localName.equalsIgnoreCase("id")) {
			   //	
			   currentGame.setId(Long.valueOf( builder.toString()));
			} else if (localName.equalsIgnoreCase("title")){
				currentGame.setName(builder.toString());
			} else if (localName.equalsIgnoreCase("eventname")){
				currentGame.setEventName(builder.toString());
			} else if (localName.equalsIgnoreCase("eventdate")){
					currentGame.setEventDate(builder.toString());
			} else if (localName.equalsIgnoreCase("description")){
				currentGame.setDescription(builder.toString());
			} else if (localName.equalsIgnoreCase("type")) {
				currentGame.setType(builder.toString());
			} else if (localName.equalsIgnoreCase("sport")) {
			    currentGame.setSport(builder.toString());
			} else if (localName.equalsIgnoreCase("league")) {
				currentGame.setLeague(builder.toString());	    
			} else if (localName.equalsIgnoreCase("numbersubscribers")) {
			    currentGame.setNumberSubscribers(Integer.parseInt(builder.toString()));
			} else if (localName.equalsIgnoreCase("team1")) {
			    currentGame.setHomeTeam(builder.toString());
			}  else if (localName.equalsIgnoreCase("team2")) {
				    currentGame.setVisitingTeam(builder.toString());
    		} else if (localName.equalsIgnoreCase("game")) {
				games.add(currentGame);
			}
			builder.setLength(0);	
		} //if
	} // endElement
	
	@Override
	public void startElement(String uri, String name, String localName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, name, localName, attributes);
		if (name.equalsIgnoreCase("game")) {
			this.currentGame = new Game();
		}  //if
	}  //startElement
	
	@Override
	public void startDocument() throws SAXException {
		    super.startDocument();
			games = new ArrayList<Game>();
			this.setStringBuilder(new StringBuilder());
	}	  //startDocument
}  //class
