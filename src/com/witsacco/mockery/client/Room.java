package com.witsacco.mockery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

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

		// addMessage( "Rich", "The Mets rule" );
		// addMessage( "Dom", "The Mets suck" );
	}

	public Widget getPanel() {
		return mainPanel;
	}

	public void addMessage( DisplayMessage message ) {

		// Add this message to the room
		messages.add( message );

		// Draw this message
		updateUI( message, messageTable.getRowCount() );
	}

	public void handleIncomingMessage( DisplayMessage message ) {
		int index = messages.indexOf( message );

		if ( index == -1 ) {
			addMessage( message );
		}
		else {
			messages.set( index, message );
			
			// Find the message in the list of messages and update its row
			updateUI( message, index );
		}
	}

	private void updateUI( DisplayMessage message, int rowId ) {
		// Set message author and body
		messageTable.setText( rowId, 0, message.getAuthorName() );
		messageTable.setText( rowId, 1, message.getBody() );

		// Apply cell-level formatting
		messageTable.getCellFormatter().addStyleName( rowId, 0, "sender-col" );
		messageTable.getCellFormatter().addStyleName( rowId, 1, "content-col" );

		if ( message.getScore() == null ) {
			// This message doesn't have a score, show a spinner
			messageTable.setText( rowId, 2, "" );
			messageTable.getCellFormatter().setStyleName( rowId, 2, "spinner" );
		}
		else {
			// This message has been scored, update it appropriately
			messageTable.setText( rowId, 2, message.getScore() );
			messageTable.getCellFormatter().setStyleName( rowId, 2, "score-col" );
		}

		// TODO Add a timestamp to this view

		// Apply row-level formatting
		messageTable.getRowFormatter().addStyleName( rowId, ( rowId % 2 == 0 ? "row-even" : "row-odd" ) );
	}
}
