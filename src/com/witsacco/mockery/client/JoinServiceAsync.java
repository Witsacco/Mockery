package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JoinServiceAsync {

	public void join( int roomId, String handle, AsyncCallback< DisplayUser > async );

}