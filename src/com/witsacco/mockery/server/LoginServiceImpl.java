package com.witsacco.mockery.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.LoginInfo;
import com.witsacco.mockery.services.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private static final long serialVersionUID = 1L;

	public LoginInfo login( String requestUri ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		LoginInfo info = new LoginInfo();

		if ( user != null ) {
			info.setLoggedIn( true );
			info.setEmailAddress( user.getEmail() );
			info.setNickname( user.getNickname() );
			info.setLogoutUrl( userService.createLogoutURL( requestUri ) );
		}
		else {
			info.setLoggedIn( false );
			info.setLoginUrl( userService.createLoginURL( requestUri ) );
		}

		return info;
	}

}