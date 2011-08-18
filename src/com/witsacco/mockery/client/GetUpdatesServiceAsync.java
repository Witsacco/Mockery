package com.witsacco.mockery.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetUpdatesServiceAsync {

	public void getUpdates( int roomId, Date d, AsyncCallback< UpdatePackage > callback );
}
