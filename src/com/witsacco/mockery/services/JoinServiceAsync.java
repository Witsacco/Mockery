package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.DisplayUser;

public interface JoinServiceAsync {

	public void join( int roomId, String handle, AsyncCallback< DisplayUser > async );

}