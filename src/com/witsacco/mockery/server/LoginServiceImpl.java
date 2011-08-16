package com.witsacco.mockery.server;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.LoginInfo;
import com.witsacco.mockery.client.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private static final long serialVersionUID = 1L;

	public LoginInfo login( String requestUri, int roomId ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo mUser = new LoginInfo();

		if ( user != null ) {
			mUser.setLoggedIn( true );
			mUser.setEmailAddress( user.getEmail() );
			mUser.setNickname( user.getNickname() );
			mUser.setLogoutUrl( userService.createLogoutURL( requestUri ) );

			// Add the user to the Standings datastore (or simply update their
			// loggedIn status)
			registerUser( user, roomId );
		}
		else {
			mUser.setLoggedIn( false );
			mUser.setLoginUrl( userService.createLoginURL( requestUri ) );
		}

		return mUser;
	}

	/*
	 * Register this user in this room
	 */
	private void registerUser( User user, int roomId ) {
		// Get a handle for the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Create the context for the room
		Key roomKey = KeyFactory.createKey( "Room", roomId );

		// Does this user exist in this room?
		Query query = new Query( "MockeryUser", roomKey );
		query.addFilter( "user", Query.FilterOperator.EQUAL, user );
		List< Entity > users = datastore.prepare( query ).asList( withLimit( 1 ) );

		if ( users.size() > 0 ) {
			// This user exists, log them in
			Entity thisUser = users.get( 0 );
			thisUser.setProperty( "loggedIn", true );
			datastore.put( thisUser );
		}
		else {
			// This user does not exist yet. Persist a new user Entity.
			Entity newUser = new Entity( "MockeryUser", roomKey );
			newUser.setProperty( "loggedIn", true );
			newUser.setProperty( "user", user );
			newUser.setProperty( "cumulativeScore", 0 );

			// Persist this new user
			datastore.put( newUser );
		}

		// TODO construct and return new DisplayUser
	}

}