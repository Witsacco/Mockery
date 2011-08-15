package com.witsacco.mockery.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetNewMessagesServiceAsync {

	public void getNewMessages( int roomId, Date d, AsyncCallback< ArrayList< DisplayMessage > > callback );
}
