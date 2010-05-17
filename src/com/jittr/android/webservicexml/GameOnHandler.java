package com.jittr.android.webservicexml;

import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jittr.android.gamemanager.games.Game;

public abstract class GameOnHandler<LISTOBJECT> extends DefaultHandler {
	
	protected StringBuilder builder;
	private ArrayList<LISTOBJECT> listOfObjects;
	
	public ArrayList<LISTOBJECT> getList() {
		return this.listOfObjects;
	}
	public void setStringBuilder(StringBuilder sb) {
		this.builder = sb;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}  //characters

	@Override
	public  void startDocument() throws SAXException {
		super.startDocument();
	}
	   
}  //class
