package com.witsacco.mockery.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "getRoomUpdates" )
public interface GetUpdatesService extends RemoteService {

	public UpdatePackage getUpdates( int roomId, Date d );

}
