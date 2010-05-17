package com.jittr.android.webservicexml.foursquare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FQUserAPI extends FourSquareHandler {
	public static final String FSID="id";
    public static final String FSUSER="user";
    public static final String FSFIRSTNAME="firstname";
    public static final String FSLASTNAME="lastname";
    public static final String FSPHOTO="photo";
    public static final String FSEMAIL="email";
    public static final String FSTWITTER = "twitter";
    public static final String FSFACEBOOK = "facebook";
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		//System.out.println("localname= " + localName +  " name = " + name + " uri = " + uri);
		if (name.equalsIgnoreCase(FSUSER)){
			this.currentMessage = new Message();
		}
	}
	@Override
	public void endElement(String uri, String name, String localName)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentMessage != null){
			if (localName.equalsIgnoreCase(FSFIRSTNAME)){
			   currentMessage.setFirstname(builder.toString());
			} else if (localName.equalsIgnoreCase(FSLASTNAME)){
				currentMessage.setLastname(builder.toString());
			} else if (localName.equalsIgnoreCase(FSID)){
				currentMessage.setId(builder.toString());
			} else if (localName.equalsIgnoreCase(FSEMAIL)){
				currentMessage.setEmail(builder.toString());
			} else if (localName.equalsIgnoreCase(FSPHOTO)) {
				currentMessage.setPhoto(builder.toString());
			} else if (localName.equalsIgnoreCase(FSTWITTER)) {
			    currentMessage.setTwitter(builder.toString());
    		} else if (localName.equalsIgnoreCase(FSFACEBOOK)) {
  	            currentMessage.setFacebook(builder.toString());
			} else if (localName.equalsIgnoreCase(FSUSER)) {
				messages.add(currentMessage);
			}
			builder.setLength(0);	
		} //if
	} // endElement
}   //class
