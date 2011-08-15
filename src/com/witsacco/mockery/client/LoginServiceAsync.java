package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	public void login( String requestUri, AsyncCallback< MockeryUser > async );

}