package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "scoreThisMessage" )
public interface MessageScoreService extends RemoteService {

	/*
	 * This method accepts a room id and a message id, and returns a message
	 * score.
	 */
	public MessageScore scoreMessage( int roomId, long messageId );

}