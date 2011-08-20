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
import com.witsacco.mockery.services.LeaveService;

public class LeaveServiceImpl extends RemoteServiceServlet implements LeaveService {

	private static final long serialVersionUID = 1L;

	public void leave( int roomId ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if ( user != null ) {
			logoutUser( roomId, user );
		}
		else {
			// TODO Throw a better error?
		}
	}

	private void logoutUser( int roomId, User user ) {
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
			// This user exists, log them out
			userEntity = users.get( 0 );
			userEntity.setProperty( "loggedIn", false );
			datastore.put( userEntity );
		}
		else {
			// TODO Throw an error?
		}
	}
}