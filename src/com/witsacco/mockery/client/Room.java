package com.witsacco.mockery.client;

import java.util.ArrayList;

public class Room {

	ArrayList< Message > messages;

	/**
	 * @param messages
	 */
	public Room() {
		messages = new ArrayList< Message >();
	}
	
	void addMessage( Message message ) {
		messages.add( message );
	}
	
	void updateMessage( int messageId ) {
		
	}
	
	
}
