package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetNewMessagesServiceAsync {

	public void getNewMessages( AsyncCallback< Void > callback );
}
