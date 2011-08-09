package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.shared.MockeryUser;

public interface LoginServiceAsync {

	public void login( String requestUri, AsyncCallback< MockeryUser > async );

}