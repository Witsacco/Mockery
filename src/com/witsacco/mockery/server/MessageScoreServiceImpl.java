package com.witsacco.mockery.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.MessageScore;
import com.witsacco.mockery.client.MessageScoreService;

public class MessageScoreServiceImpl extends RemoteServiceServlet implements MessageScoreService {

	private static final long serialVersionUID = 1L;

	private static final MessageJudge JUDGE = MessageJudge.getInstance();

	/*
	 * Scores a message given its id. Returns null if the message cannot be
	 * found.
	 */
	@Override
	public MessageScore scoreMessage( int roomId, long messageId ) {
		MessageScore result = null;

		// Get a handle for the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Look up the message to be scored
		Entity message;
		try {
			// Get the given message in the given room
			Key roomKey = KeyFactory.createKey( "Room", roomId );
			message = datastore.get( KeyFactory.createKey( roomKey, "Message", messageId ) );
		}
		catch ( EntityNotFoundException e ) {
			// TODO Better handle this error, and probably return null
			e.printStackTrace();

			return null;
		}

		System.out.println( message );

		result = JUDGE.evaluateMessage( ( String ) message.getProperty( "body" ) );

		message.setProperty( "score", result.getScore() );
		message.setProperty( "scoreReason", result.getExplanation() );

		datastore.put( message );

		System.out.println( message );

		return result;

	}

}