package com.jittr.android.gamemanager;

import android.os.Parcelable;

//TODO - make it implement Parcelable
public class GameUserSettings extends baseTablePojo  {
private int userID=1;
private String twitter; //= null; //"juliomiy";
private String facebook; //=null; //"juliomiyares@mac.com";
private String foursquare;//=null; //"9173702880";
private boolean defaultTwitter; //=true;
private boolean defaultFacebook;//=false;
private boolean defaultFoursquare;//=false;
private String  twitterOAuthToken;
private String  twitterOAuthTokenSecret;
private String  foursquareOAuthToken;
private String  foursquareOAuthTokenSecret;

public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public String getTwitterOAuthToken() {
	return twitterOAuthToken;
}
public void setTwitterOAuthToken(String twitterOAuthToken) {
	this.twitterOAuthToken = twitterOAuthToken;
}
public String getTwitterOAuthTokenSecret() {
	return twitterOAuthTokenSecret;
}
public void setTwitterOAuthTokenSecret(String twitterOAuthTokenSecret) {
	this.twitterOAuthTokenSecret = twitterOAuthTokenSecret;
}
public String getFoursquareOAuthToken() {
	return foursquareOAuthToken;
}
public void setFoursquareOAuthToken(String foursquareOAuthToken) {
	this.foursquareOAuthToken = foursquareOAuthToken;
}
public String getFoursquareOAuthTokenSecret() {
	return foursquareOAuthTokenSecret;
}
public void setFoursquareOAuthTokenSecret(String foursquareOAuthTokenSecret) {
	this.foursquareOAuthTokenSecret = foursquareOAuthTokenSecret;
}
public String getTwitter() {
	return this.twitter;
}
public void setTwitter(String twitter) {
	this.twitter = twitter;
}
public String getFacebook() {
	return this.facebook;
}
public void setFacebook(String facebook) {
	this.facebook = facebook;
}
public String getFoursquare() {
	return this.foursquare;
}
public void setFoursquare(String foursquare) {
	this.foursquare = foursquare;
}
public boolean isDefaultTwitter() {
	return this.defaultTwitter;
}
public void setDefaultTwitter(boolean defaultTwitter) {
	this.defaultTwitter = defaultTwitter;
}
public boolean isDefaultFacebook() {
	return this.defaultFacebook;
}
public void setDefaultFacebook(boolean defaultFacebook) {
	this.defaultFacebook = defaultFacebook;
}
public boolean isDefaultFoursquare() {
	return this.defaultFoursquare;
}
public void setDefaultFoursquare(boolean defaultFoursquare) {
	this.defaultFoursquare = defaultFoursquare;
}
/*public String getModifiedDate() {
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    return ts.toString();
}
*/
} //class
