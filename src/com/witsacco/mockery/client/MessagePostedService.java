package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.shared.Message;

@RemoteServiceRelativePath( "messagePosted" )
public interface MessagePostedService extends RemoteService {

	/*
	 * This accepts a message to be persisted and returns the message on
	 * successful save.
	 */
	public Message postMessage( Message message );

}
