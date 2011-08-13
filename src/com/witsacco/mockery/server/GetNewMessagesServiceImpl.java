package com.witsacco.mockery.server;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.ArrayList;
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
import com.witsacco.mockery.client.GetNewMessagesService;
import com.witsacco.mockery.shared.DisplayMessage;

public class GetNewMessagesServiceImpl extends RemoteServiceServlet implements GetNewMessagesService {

	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList< DisplayMessage > getNewMessages( Date cutoff ) {

		// Instantiate a handle to the data store
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Grab the current user so we can ignore their new posts
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// For now just use room number 1
		Key persistanceKey = KeyFactory.createKey( "MessageRoom", 1 );

		Query query = new Query( "PostedMessage", persistanceKey );

		// Add a filter for messages greater than the cutoff
		query.addFilter( "date", Query.FilterOperator.GREATER_THAN, cutoff );

		// Sort the new messages in descending order by date
		query.addSort( "date", Query.SortDirection.DESCENDING );

		// Retrieve the new messages from the query (limit to 10 for now)
		List< Entity > messages = datastore.prepare( query ).asList( withLimit( 10 ) );

		// Create a place to hold new messages
		ArrayList< DisplayMessage > foundMessages = new ArrayList< DisplayMessage >();

		// Iterate through the set of messages we found
		for ( Entity message : messages ) {

			// Grab the user and the body of the message
			User postedBy = ( User ) message.getProperty( "user" );
			String messageBody = ( String ) message.getProperty( "message" );

			// Ignore this message if it's from the user himself
			if ( !postedBy.equals( user ) ) {

				// Create a new DisplayMessage and add it to the result set
				foundMessages.add( new DisplayMessage( postedBy.getNickname(), messageBody ) );
			}
		}

		// Return the set of newly-posted messages
		return foundMessages;
	}
}
