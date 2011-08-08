package com.witsacco.mockery.server;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.MessagePostedService;

public class MessagePostedServiceImpl extends RemoteServiceServlet implements
		MessagePostedService {

	private static final long serialVersionUID = 1L;

	@Override
	public void postMessage( int roomId, String user, String content ) {

		System.out.println( "Message received" );
		
//		// Grab the room ID passed to this request
//		Key persistanceKey = KeyFactory.createKey( "MessageRoom", roomId );
//
//		// Grab the message content passed to this request
//		Date date = new Date();
//
//		// Create a new entity to store to the database
//		Entity postedMessage = new Entity( "MessageRoom", persistanceKey );
//		postedMessage.setProperty( "user", user );
//		postedMessage.setProperty( "date", date );
//		postedMessage.setProperty( "content", content );
//
//		// Store the data
//		DatastoreService datastore = DatastoreServiceFactory
//				.getDatastoreService();
//		datastore.put( postedMessage );
	}
}
