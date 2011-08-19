package com.witsacco.mockery.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.witsacco.mockery.client.LoginInfo;

@RemoteServiceRelativePath( "login" )
public interface LoginService extends RemoteService {

	public LoginInfo login( String requestUri );

}