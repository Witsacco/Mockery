package com.witsacco.mockery.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class PostMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws IOException {

		// Grab the current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Grab the room ID passed to this request
		String roomId = req.getParameter( "roomId" );
		Key persistanceKey = KeyFactory.createKey( "MessageRoom", roomId );

		// Grab the message content passed to this request
		String content = req.getParameter( "content" );
		Date date = new Date();

		// Create a new entity to store to the database
		Entity postedMessage = new Entity( "MessageRoom", persistanceKey );
		postedMessage.setProperty( "user", user );
		postedMessage.setProperty( "date", date );
		postedMessage.setProperty( "content", content );

		// Store the data
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put( postedMessage );
	}
}
