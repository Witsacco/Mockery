package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "scoreMessage" )
public interface MessageScoreService extends RemoteService {

	/*
	 * This method accepts the id of a message to be scored and its room id, and
	 * returns the MessageScore object upon successful scoring (or null if there
	 * was an error).
	 */
	public MessageScore scoreMessage( int roomId, long messageId );

}