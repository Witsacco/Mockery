package com.witsacco.mockery.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.witsacco.mockery.client.GetNewMessagesService;
import com.witsacco.mockery.shared.DisplayMessage;

public class GetNewMessagesServiceImpl extends RemoteServiceServlet implements GetNewMessagesService {

	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList< DisplayMessage > getNewMessages() {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// For now just use room number 1
		Key persistanceKey = KeyFactory.createKey( "MessageRoom", 1 );

		Query query = new Query( "PostedMessage", persistanceKey ).addSort( "date", Query.SortDirection.DESCENDING );

		List< Entity > messages = datastore.prepare( query ).asList( FetchOptions.Builder.withLimit( 5 ) );

		ArrayList< DisplayMessage > foundMessages = new ArrayList< DisplayMessage >();
		
		if ( messages.isEmpty() ) {
			System.out.println( "No messages found" );
		}
		else {
			for ( Entity message : messages ) {
				User user = ( User ) message.getProperty( "user" );
				Date date = ( Date ) message.getProperty( "date" );
				String messageBody = ( String ) message.getProperty( "message" );
				
				//TODO Add logic for only getting recent messages
				
				foundMessages.add( new DisplayMessage( user.getNickname(), messageBody ) );
			}
		}

		return foundMessages;
	}
}
