package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mockery implements EntryPoint {

	Scoreboard scoreboard;
	Room room;
	MockeryUser user;
	InputField inputField;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
 		scoreboard = new Scoreboard();
 		room = new Room();
 		//TODO get mockery user
 		inputField = new InputField();

	}
	
	public void createMessage() {
		
	}
	
	
//	public void createMessageCallback() {
//		room.addMessage();
//		room.redraw();
//	}
		
	public void updateMessage() {
		
	}

//	public void updateMessageCallback() {
//		room.updateMessage();
//		scoreboard.getStanding();
//		scoreboard.redraw();
//	}
}
