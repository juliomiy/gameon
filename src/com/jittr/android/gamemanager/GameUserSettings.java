package com.jittr.android.gamemanager;

import android.os.Parcel;
import android.os.Parcelable;

//TODO - make it implement Parcelable
public class GameUserSettings extends baseTablePojo implements Parcelable  {
private int userID;
private String twitterID; //= null; //"juliomiy";
private String facebookID; //=null; //"juliomiyares@mac.com";
private String foursquareID;//=null; //"9173702880";
private String aimID;
private String icqID;
private boolean twitterDefault; //=true;
private boolean facebookDefault;//=false;
private boolean foursquareDefault;//=false;
private String  twitterOAuthToken;
private String  twitterOAuthTokenSecret;
private String  facebookOAuthToken;
private String  facebookOAuthTokenSecret;
private String  foursquareOAuthToken;
private String  foursquareOAuthTokenSecret;

public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public String getTwitterID() {
	return twitterID;
}
public void setTwitterID(String twitterID) {
	this.twitterID = twitterID;
}
public String getFacebookID() {
	return facebookID;
}
public void setFacebookID(String facebookID) {
	this.facebookID = facebookID;
}
public String getFoursquareID() {
	return foursquareID;
}
public void setFoursquareID(String foursquareID) {
	this.foursquareID = foursquareID;
}
public String getAimID() {
	return aimID;
}
public void setAimID(String aimID) {
	this.aimID = aimID;
}
public String getIcqID() {
	return icqID;
}
public void setIcqID(String icqID) {
	this.icqID = icqID;
}
public boolean isTwitterDefault() {
	return twitterDefault;
}
public void setTwitterDefault(boolean twitterDefault) {
	this.twitterDefault = twitterDefault;
}
public boolean isFacebookDefault() {
	return facebookDefault;
}
public void setFacebookDefault(boolean facebookDefault) {
	this.facebookDefault = facebookDefault;
}
public boolean isFoursquareDefault() {
	return foursquareDefault;
}
public void setFoursquareDefault(boolean foursquareDefault) {
	this.foursquareDefault = foursquareDefault;
}
public String getFacebookOAuthToken() {
	return facebookOAuthToken;
}
public void setFacebookOAuthToken(String facebookOAuthToken) {
	this.facebookOAuthToken = facebookOAuthToken;
}
public String getFacebookOAuthTokenSecret() {
	return facebookOAuthTokenSecret;
}
public void setFacebookOAuthTokenSecret(String facebookOAuthTokenSecret) {
	this.facebookOAuthTokenSecret = facebookOAuthTokenSecret;
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
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}
public void writeToParcel(Parcel dest, int flags) {
	// TODO Auto-generated method stub
	
}
} //class
