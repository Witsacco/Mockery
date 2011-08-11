package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "getNewMessages" )
public interface GetNewMessagesService extends RemoteService {

	public void getNewMessages();

}
