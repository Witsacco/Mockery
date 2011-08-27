package com.witsacco.mockery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.resources.RoomCSS;

public class Room extends ScrollPanel {

	ArrayList< DisplayMessage > messages;

	private FlexTable messageTable;

	private RoomCSS css = MockeryResources.INSTANCE.roomCss();

	public Room() {
		// Invoke superclass constructor
		super();
		
		messages = new ArrayList< DisplayMessage >();

		// Set up the Room UI components
		initializeUI();
	}

	private void initializeUI() {
		
		// Inject CSS
		css.ensureInjected();
		
		// Add basic styling
		addStyleName( css.roomPanel() );
		
		HorizontalPanel roomPanel = new HorizontalPanel();
		roomPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
		roomPanel.setWidth( "100%" );
		
		messageTable = new FlexTable();
		messageTable.addStyleName( css.messageTable() );

		roomPanel.add( messageTable );
		
		add( roomPanel );
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

		if ( rowId == 0 ) {
			messageTable.getRowFormatter().addStyleName( 0, css.firstRow() );
		}
		
		// Apply cell-level formatting
		messageTable.getCellFormatter().addStyleName( rowId, 0, css.senderCol() );
		messageTable.getCellFormatter().addStyleName( rowId, 1, css.contentCol() );

		if ( message.getScore() == null ) {
			// This message doesn't have a score, show a spinner
			messageTable.setText( rowId, 2, "" );
			messageTable.getCellFormatter().setStyleName( rowId, 2, css.spinner() );
		}
		else {
			// This message has been scored, update it appropriately
			messageTable.setText( rowId, 2, message.getScore() );
			messageTable.getCellFormatter().setStyleName( rowId, 2, css.scoreCol() );
		}

		// TODO Add a timestamp to this view

		// Apply row-level formatting
		messageTable.getRowFormatter().addStyleName( rowId, ( rowId % 2 == 0 ? css.rowEven() : css.rowOdd() ) );
	}
}
