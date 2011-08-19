package com.witsacco.mockery.services;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.client.UpdatePackage;

@RemoteServiceRelativePath( "getRoomUpdates" )
public interface GetUpdatesService extends RemoteService {

	public UpdatePackage getUpdates( int roomId, Date d );

}
