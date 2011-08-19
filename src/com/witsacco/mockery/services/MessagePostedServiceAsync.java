package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.DisplayMessage;

public interface MessagePostedServiceAsync {

	public void postMessage( int roomId, String messageBody, AsyncCallback< DisplayMessage > callback );

}
