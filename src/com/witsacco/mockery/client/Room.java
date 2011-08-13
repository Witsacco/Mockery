package com.witsacco.mockery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.witsacco.mockery.shared.DisplayMessage;
import com.witsacco.mockery.shared.Message;

public class Room {

	ArrayList< DisplayMessage > messages;

	private ScrollPanel mainPanel;
	private FlexTable messageTable;

	public Room() {
		messages = new ArrayList< DisplayMessage >();

		// Set up the Room UI components
		initializeUI();
	}

	private void initializeUI() {
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( "room-panel" );

		messageTable = new FlexTable();
		messageTable.addStyleName( "message-table" );

		mainPanel.add( messageTable );
		
//		addMessage( "Rich", "The Mets rule" );
//		addMessage( "Dom", "The Mets suck" );
	}

	public Widget getPanel() {
		return mainPanel;
	}

	public void addMessage( DisplayMessage message ) {

		// Add this message to the room
		messages.add( message );

		// Redraw this message
		updateUI( message );
	}
	
	//
	// void updateMessage( Message message ) {
	// Find the message in messages
	// Swap it or set the score
	// Update UI
	//
	// }

	private void updateUI( DisplayMessage message ) {
		final int rowCount = messageTable.getRowCount();

		messageTable.setText( rowCount, 0, message.getNickname() );
		messageTable.setText( rowCount, 1, message.getBody() );

		// TODO Add a timestamp to this view

		// Apply cell-level formatting
		messageTable.getCellFormatter().addStyleName( rowCount, 0, "sender-col" );
		messageTable.getCellFormatter().addStyleName( rowCount, 1, "content-col" );

		// Apply row-level formatting
		messageTable.getRowFormatter().addStyleName( rowCount, ( rowCount % 2 == 0 ? "row-even" : "row-odd" ) );
	}
}
