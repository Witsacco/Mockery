package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.client.DisplayMessage;

@RemoteServiceRelativePath( "postMessage" )
public interface MessagePostedService extends RemoteService {

	/*
	 * This accepts the content of a message to be created and the id of the
	 * room within which to create it, then persists the message, and returns a
	 * DisplayMessage for display in the UI.
	 */
	public DisplayMessage postMessage( int roomId, String messageBody );

}