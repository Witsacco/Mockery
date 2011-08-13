package com.witsacco.mockery.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.shared.DisplayMessage;

@RemoteServiceRelativePath( "getNewMessages" )
public interface GetNewMessagesService extends RemoteService {

	public ArrayList< DisplayMessage > getNewMessages( Date d );

}
