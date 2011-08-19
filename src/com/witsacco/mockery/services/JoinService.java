package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.client.DisplayUser;

@RemoteServiceRelativePath( "join" )
public interface JoinService extends RemoteService {

	public DisplayUser join( int roomId, String handle );

}