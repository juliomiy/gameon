package com.jittr.android.webservicexml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class GameOnHandler extends DefaultHandler {
	
	protected StringBuilder builder;
	
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
