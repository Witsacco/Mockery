package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.DisplayMessage;

public interface MessageScoreServiceAsync {

	public void scoreMessage( int roomId, DisplayMessage message, AsyncCallback< DisplayMessage > callback );

}
