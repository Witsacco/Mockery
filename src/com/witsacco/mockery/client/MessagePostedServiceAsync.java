package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagePostedServiceAsync {

	public void postMessage( int roomId, String messageBody, AsyncCallback< DisplayMessage > callback );

}
