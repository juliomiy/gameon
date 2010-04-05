package com.jittr.android.webservicexml;

import java.util.List;

import com.jittr.android.gamemanager.games.Game;
import com.jittr.android.webservicexml.foursquare.Message;

public interface FeedParser {

		List<Game> parse();

}
