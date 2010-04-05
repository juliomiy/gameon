package com.jittr.android.webservicexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BaseFeedParser implements FeedParser {
	private final URL feedUrl;
	public static final String FSID="id";
    public static final String FSUSER="user";
    public static final String FSFIRSTNAME="firstname";
    public static final String FSLASTNAME="lastname";
    public static final String FSPHOTO="photo";
    public static final String FSEMAIL="email";
    public static final String FSTWITTER = "twitter";
    public static final String FSFACEBOOK = "facebook";
    //static final int FSFRIENDSAPI=0;
    //static final int FSUSERAPI=1;
    
	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

} //class

