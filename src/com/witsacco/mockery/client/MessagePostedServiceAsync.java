package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagePostedServiceAsync {

	void postMessage( int roomId, String user, String content, AsyncCallback< Void > callback );
}
