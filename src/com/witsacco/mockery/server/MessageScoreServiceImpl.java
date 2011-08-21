package com.witsacco.mockery.server;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.DisplayMessage;
import com.witsacco.mockery.client.MessageScore;
import com.witsacco.mockery.services.MessageScoreService;

public class MessageScoreServiceImpl extends RemoteServiceServlet implements MessageScoreService {

	private static final long serialVersionUID = 1L;

	private static final MessageJudge JUDGE = MessageJudge.getInstance();

	/*
	 * Scores a message given its id and the id of the room it belongs to. Returns null if an error code, otherwise
	 * the score object.
	 */
	@Override
	public DisplayMessage scoreMessage( int roomId, DisplayMessage dMessage ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Get a handle for the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key roomKey = KeyFactory.createKey( "Room", roomId );

		long messageId = dMessage.getMessageId();
		
		// Look up the message to be scored
		Entity message;
		
		try {
			// Get the given message in the given room
			message = datastore.get( KeyFactory.createKey( roomKey, "Message", messageId ) );
		}
		catch ( EntityNotFoundException e ) {
			e.printStackTrace();

			return null;
		}

		MessageScore result = JUDGE.evaluateMessage( ( String ) message.getProperty( "body" ) );

		// Set the score into the entity to be persisted and the display message to be returned
		message.setProperty( "score", result.getScore() );
		dMessage.setScore( result.getScore() );
		
		// Set the reason into the entity to be persisted and the display message to be returned
		message.setProperty( "scoreReason", result.getExplanation() );
		dMessage.setScoreReason( result.getExplanation() );
		
		// Set the update time on this entity
		message.setProperty( "updateTime", new Date() );

		// Store the entity into the datastore
		datastore.put( message );

		// Update the author's cumulative score
		Query query = new Query( "MockeryUser", roomKey );
		query.addFilter( "user", Query.FilterOperator.EQUAL, user );
		List< Entity > users = datastore.prepare( query ).asList( withLimit( 1 ) );

		Entity userEntity;

		if ( users.size() > 0 ) {
			// This user exists, log them in
			userEntity = users.get( 0 );

			Long currentScore = ( Long ) userEntity.getProperty( "cumulativeScore" );
			currentScore += result.getScore();

			userEntity.setProperty( "cumulativeScore", currentScore );

			datastore.put( userEntity );
		}
		else {
			// TODO Throw an error here? Invalid user?
		}

		return dMessage;
	}
}