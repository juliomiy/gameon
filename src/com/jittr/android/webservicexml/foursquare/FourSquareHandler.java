package com.jittr.android.webservicexml.foursquare;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class FourSquareHandler extends DefaultHandler {
	protected List<Message> messages;
	protected Message currentMessage;
	protected StringBuilder builder;
	
	public List<Message> getMessages(){
		return this.messages;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		messages = new ArrayList<Message>();
		builder = new StringBuilder();
	}
} //class
