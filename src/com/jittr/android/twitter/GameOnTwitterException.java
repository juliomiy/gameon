package com.jittr.android.twitter;

import java.util.Date;

import com.jittr.android.exception.GameOnException;

import android.util.Log;

/* Twitter specific Exception class 
 * log error to log files, (done in parent) 
 * set gameon specific Error Code
 * store a user centric error message
 * save stacktrace when in debug mode  (done in parent)
 */
public class GameOnTwitterException extends GameOnException {

	String userFriendlyMessage;
	private static final String TAG="GameOnTwitterException";
	
	public GameOnTwitterException(String userMessage ,String errorMessage, Exception e, String generatorTag) {
        super(errorMessage,e,generatorTag);   
        userFriendlyMessage = userMessage;
        
	}  //constructor

	/* type of message displayed to user - non-technical */
	public String getUserFriendlyMessage() {
		return userFriendlyMessage;
	}
}  //GameOnTwitterException
