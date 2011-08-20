package com.witsacco.mockery.server;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.Date;
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
import com.witsacco.mockery.client.DisplayMessage;
import com.witsacco.mockery.services.MessagePostedService;

public class MessagePostedServiceImpl extends RemoteServiceServlet implements MessagePostedService {

	private static final long serialVersionUID = 1L;

	@Override
	public DisplayMessage postMessage( int roomId, String messageBody ) {

		// Grab the current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Get the current time
		Date time = new Date();

		// Create the context for the room
		Key roomKey = KeyFactory.createKey( "Room", roomId );

		// Create the message to persist in this room
		Entity message = new Entity( "Message", roomKey );
		message.setProperty( "user", user );
		message.setProperty( "body", messageBody );
		message.setProperty( "createTime", time );
		message.setProperty( "updateTime", time );

		// Get a handle to the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Store the message and grab its key
		Key messageKey = datastore.put( message );

		// Query the database to get the user's handle
		Query userQuery = new Query( "MockeryUser", roomKey );
		userQuery.addFilter( "user", Query.FilterOperator.EQUAL, user );
		List< Entity > users = datastore.prepare( userQuery ).asList( withLimit( 1 ) ); // TODO Error check?

		// Return the message to the client for display
		return new DisplayMessage( messageKey.getId(), roomId, messageBody, ( String ) users.get( 0 ).getProperty(
				"handle" ) );

	}
}