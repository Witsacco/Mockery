package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "join" )
public interface JoinService extends RemoteService {

	public DisplayUser join( int roomId, String handle );

}