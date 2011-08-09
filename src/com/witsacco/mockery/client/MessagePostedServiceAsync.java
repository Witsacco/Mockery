package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.shared.Message;

public interface MessagePostedServiceAsync {

	public void postMessage( Message message, AsyncCallback< Message > callback );

}
