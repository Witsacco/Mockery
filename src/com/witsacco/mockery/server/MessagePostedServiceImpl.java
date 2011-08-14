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
import com.witsacco.mockery.client.MessagePostedService;
import com.witsacco.mockery.shared.Message;

public class MessagePostedServiceImpl extends RemoteServiceServlet implements MessagePostedService {

	private static final long serialVersionUID = 1L;

	@Override
	public Message postMessage( Message message ) {

		// Grab the current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		// Grab the room ID passed to this request
		Key persistanceKey = KeyFactory.createKey( "MessageRoom", message.getRoomId() );

		// Create a new entity to store to the database
		Entity postedMessage = new Entity( "PostedMessage", persistanceKey );
		postedMessage.setProperty( "user", user );
		postedMessage.setProperty( "date", new Date() );
		postedMessage.setProperty( "message", message.getBody() );

		// Store the data
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put( postedMessage );

		// Return the message to the client for display
		return message;

	}
}
