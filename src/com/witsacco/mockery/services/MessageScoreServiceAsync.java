package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.MessageScore;

public interface MessageScoreServiceAsync {

	public void scoreMessage( int roomId, long messageId, AsyncCallback< MessageScore > callback );

}
