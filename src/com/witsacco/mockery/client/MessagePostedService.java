package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("messagePosted")
public interface MessagePostedService extends RemoteService {
	
	void postMessage( int roomId, String user, String content );
}
