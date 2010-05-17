package com.jittr.android.webservicexml;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import com.jittr.android.gamemanager.games.Game;

/* interface for parsing XML over HTTP webservices for GameOn
 * TODO - eliminate the compile time dependency on a specific object , in this case
 * Game and replace with a generic. The specificity should be at a higher level
 */
public interface FeedParser <LISTOBJECT> {

		List<LISTOBJECT> parse() throws GOWebServiceAPIException;
//		List<LISTOBJECT> parse(InputStream,XMLHANDLER) throws GOWebServiceAPIException;
}
