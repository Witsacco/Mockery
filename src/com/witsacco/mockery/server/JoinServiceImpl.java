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
import com.witsacco.mockery.client.DisplayUser;
import com.witsacco.mockery.client.JoinService;

public class JoinServiceImpl extends RemoteServiceServlet implements JoinService {

	private static final long serialVersionUID = 1L;

	/*
	 * @roomId The room to be joined
	 * 
	 * @handle The user's requested handle
	 */
	public DisplayUser join( int roomId, String handle ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if ( user != null ) {
			// Add the user to the Standings datastore (or simply update their
			// loggedIn status) and return them
			return registerUser( user, roomId, handle );
		}
		else {
			// TODO Throw a better error?
		}

		return null;
	}

	/*
	 * Register this user in this room
	 */
	private DisplayUser registerUser( User user, int roomId, String handle ) {
		// Get a handle for the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Create the context for the room
		Key roomKey = KeyFactory.createKey( "Room", roomId );

		// Does this user exist in this room?
		Query query = new Query( "MockeryUser", roomKey );
		query.addFilter( "user", Query.FilterOperator.EQUAL, user );
		List< Entity > users = datastore.prepare( query ).asList( withLimit( 1 ) );

		Entity userEntity;

		if ( users.size() > 0 ) {
			// This user exists, log them in
			userEntity = users.get( 0 );
			userEntity.setProperty( "loggedIn", true );
			datastore.put( userEntity );
		}
		else {
			// This user does not exist yet. Persist a new user Entity.
			userEntity = new Entity( "MockeryUser", roomKey );
			userEntity.setProperty( "user", user );
			userEntity.setProperty( "loggedIn", true );
			userEntity.setProperty( "cumulativeScore", 0 );
			userEntity.setProperty( "handle", handle ); // TODO Should this be persisted?

			// Persist this new user
			datastore.put( userEntity );
		}

		return new DisplayUser( handle, ( Integer ) userEntity.getProperty( "cumulativeScore" ), true );
	}

}