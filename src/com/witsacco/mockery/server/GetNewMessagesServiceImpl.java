package com.witsacco.mockery.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.GetNewMessagesService;

public class GetNewMessagesServiceImpl extends RemoteServiceServlet implements
		GetNewMessagesService {

	private static final long serialVersionUID = 1L;

	@Override
	public void getNewMessages() {
//		System.out.println( "Getting new messages" );
	}
}
