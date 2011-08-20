package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LeaveServiceAsync {

	public void leave( int roomId, AsyncCallback< Void > async );

}
