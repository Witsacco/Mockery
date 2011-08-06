package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Room {

//	ArrayList< Message > messages;

	private ScrollPanel mainPanel;
	
	public Room() {
//		messages = new ArrayList< Message >();
		
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( "room-panel" );
	}

	public Widget getPanel() {
		return mainPanel;
	}
	
//	void addMessage( Message message ) {
//		messages.add( message );
//	}
//	
//	void updateMessage( int messageId ) {
//		
//	}
}
