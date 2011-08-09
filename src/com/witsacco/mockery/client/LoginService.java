package com.witsacco.mockery.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.shared.MockeryUser;

@RemoteServiceRelativePath( "login" )
public interface LoginService extends RemoteService {

	public MockeryUser login( String requestUri );

}