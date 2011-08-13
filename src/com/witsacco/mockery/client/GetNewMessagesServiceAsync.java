package com.witsacco.mockery.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.shared.DisplayMessage;

public interface GetNewMessagesServiceAsync {

	public void getNewMessages( Date d, AsyncCallback< ArrayList< DisplayMessage > > callback );
}
