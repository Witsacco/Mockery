package com.witsacco.mockery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.shared.DisplayMessage;

public interface GetNewMessagesServiceAsync {

	public void getNewMessages( AsyncCallback< ArrayList< DisplayMessage > > callback );
}
