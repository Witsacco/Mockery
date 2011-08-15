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
import com.witsacco.mockery.client.DisplayMessage;
import com.witsacco.mockery.client.GetNewMessagesService;

public class GetNewMessagesServiceImpl extends RemoteServiceServlet implements GetNewMessagesService {

	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList< DisplayMessage > getNewMessages( int roomId, Date cutoff ) {

		// Instantiate a handle to the data store
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Grab the current user so we can ignore their new posts
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Key roomKey = KeyFactory.createKey( "Room", roomId );

		Query query = new Query( "Message", roomKey );

		// Add a filter for messages greater than the cutoff
		query.addFilter( "createTime", Query.FilterOperator.GREATER_THAN, cutoff );

		// Sort the new messages in descending order by date
		query.addSort( "createTime", Query.SortDirection.ASCENDING );

		// Retrieve the new messages from the query (limit to 10 for now)
		List< Entity > messages = datastore.prepare( query ).asList( withLimit( 10 ) );

		// Create a place to hold new messages
		ArrayList< DisplayMessage > foundMessages = new ArrayList< DisplayMessage >();

		// Iterate through the set of messages we found
		for ( Entity message : messages ) {

			// Get message properties
			long id = message.getKey().getId();
			String messageBody = ( String ) message.getProperty( "body" );
			User postedBy = ( User ) message.getProperty( "user" );

			// Ignore this message if it's from the user himself
			if ( !postedBy.equals( user ) ) {

				// Create a new DisplayMessage and add it to the result set

				if ( message.getProperties().containsKey( "score" ) ) {
					Integer score = new Integer( message.getProperty( "score" ) + "" ); // TODO Cleanup this hack
					String scoreReason = ( String ) message.getProperty( "scoreReason" );
					foundMessages.add( new DisplayMessage( id, roomId, messageBody, postedBy.getNickname(), score,
							scoreReason ) );
				}
				else {
					foundMessages.add( new DisplayMessage( id, roomId, messageBody, postedBy.getNickname() ) );
				}
			}
		}

		// Return the set of newly-posted messages
		return foundMessages;
	}
}
