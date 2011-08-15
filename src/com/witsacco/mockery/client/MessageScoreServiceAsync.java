package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessageScoreServiceAsync {

	public void scoreMessage( int roomId, long messageId, AsyncCallback< MessageScore > callback );

}
