package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	public void login( String requestUri, int roomId, AsyncCallback< LoginInfo > async );

}