package com.witsacco.mockery.server;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.DisplayMessage;
import com.witsacco.mockery.client.MessagePostedService;

public class MessagePostedServiceImpl extends RemoteServiceServlet implements MessagePostedService {

	private static final long serialVersionUID = 1L;

	@Override
	public DisplayMessage postMessage( int roomId, String messageBody ) {

		// Grab the current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Create the context for the room
		Key roomKey = KeyFactory.createKey( "Room", roomId );

		// Create the message to persist in this room
		Entity message = new Entity( "Message", roomKey );
		message.setProperty( "user", user );
		message.setProperty( "body", messageBody );
		message.setProperty( "createTime", new Date() );

		// Get a handle to the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Store the message and grab its key
		Key messageKey = datastore.put( message );

		// Return the message to the client for display
		return new DisplayMessage( messageKey.getId(), roomId, messageBody, user.getNickname() );

	}
}