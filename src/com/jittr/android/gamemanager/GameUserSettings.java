package com.jittr.android.gamemanager;

public class GameUserSettings extends baseTablePojo {
private int userID=1;
private String twitter="juliomiy";
private String facebook="juliomiyares@mac.com";
private String foursquare="9173702880";
private boolean defaultTwitter=true;
private boolean defaultFacebook=false;
private boolean defaultFoursquare=false;
public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public String getTwitter() {
	return twitter;
}
public void setTwitter(String twitter) {
	this.twitter = twitter;
}
public String getFacebook() {
	return facebook;
}
public void setFacebook(String facebook) {
	this.facebook = facebook;
}
public String getFoursquare() {
	return foursquare;
}
public void setFoursquare(String foursquare) {
	this.foursquare = foursquare;
}
public boolean isDefaultTwitter() {
	return defaultTwitter;
}
public void setDefaultTwitter(boolean defaultTwitter) {
	this.defaultTwitter = defaultTwitter;
}
public boolean isDefaultFacebook() {
	return defaultFacebook;
}
public void setDefaultFacebook(boolean defaultFacebook) {
	this.defaultFacebook = defaultFacebook;
}
public boolean isDefaultFoursquare() {
	return defaultFoursquare;
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
