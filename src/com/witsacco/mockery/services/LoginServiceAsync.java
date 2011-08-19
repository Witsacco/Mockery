package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.LoginInfo;

public interface LoginServiceAsync {

	public void login( String requestUri, AsyncCallback< LoginInfo > async );

}