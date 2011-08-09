package com.witsacco.mockery.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.LoginService;
import com.witsacco.mockery.shared.MockeryUser;

@SuppressWarnings( "serial" )
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	public MockeryUser login( String requestUri ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		MockeryUser mUser = new MockeryUser();

		if ( user != null ) {
			mUser.setLoggedIn( true );
			mUser.setEmailAddress( user.getEmail() );
			mUser.setNickname( user.getNickname() );
			mUser.setLogoutUrl( userService.createLogoutURL( requestUri ) );
		}
		else {
			mUser.setLoggedIn( false );
			mUser.setLoginUrl( userService.createLoginURL( requestUri ) );
		}

		return mUser;
	}

}