package com.jittr.android.webservicexml;
import java.util.Date;

import android.util.Log;

public class GOWebServiceAPIException extends Exception {

	private String TAG;
	private String message;
	
	public GOWebServiceAPIException(String message) {
	       this.message=message;
	       this.TAG="Unknown";
	       logIt();
	}
	public GOWebServiceAPIException(String tag ,String message) {
	       this.message=message;
	       this.TAG=tag;
	       logIt();
	}
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void logIt() {
       Log.e(this.TAG, new Date(System.currentTimeMillis()).toString() + " " + this.message);
	}
}  //class
