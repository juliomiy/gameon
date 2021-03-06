package com.jittr.android.exception;
import java.util.Date;

import android.util.Log;
/* Abstract class for GameOn Exceptions
 * 
 */
public abstract class GameOnException extends Exception {
	protected String gaErrorMessage;  //generated by the GameOn application itself based on context
	protected Exception exception;
	protected String generatorTag;
	private static final String TAG="GameOnException";

	protected GameOnException() {
	}
	protected GameOnException(String errorMessage, Exception e, String tag) {
		gaErrorMessage = errorMessage;
		generatorTag = tag;
		exception = e;
	}  // constructor
	
	protected void logIt() {
	    Log.e(generatorTag, new Date(System.currentTimeMillis()).toString() + " " + gaErrorMessage);
	    Log.getStackTraceString(exception);
	} //log

}   //GameOnException
