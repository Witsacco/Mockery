package com.witsacco.mockery.services;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.client.UpdatePackage;

public interface GetUpdatesServiceAsync {

	public void getUpdates( int roomId, Date d, AsyncCallback< UpdatePackage > callback );
}
