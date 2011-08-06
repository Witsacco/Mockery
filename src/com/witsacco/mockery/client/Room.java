package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Room {

	// ArrayList< Message > messages;

	private ScrollPanel mainPanel;
	private FlexTable messageTable;

	public Room() {
		// messages = new ArrayList< Message >();

		// Set up the Room UI components
		initializeUI();
	}

	private void initializeUI() {
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( "room-panel" );

		messageTable = new FlexTable();
		messageTable.addStyleName( "message-table" );
		
		mainPanel.add( messageTable );

		HTMLTable.ColumnFormatter colFormatter = messageTable.getColumnFormatter();
		colFormatter.addStyleName( 0, "sender-col" );
		colFormatter.addStyleName( 1, "content-col" );

		addMessage( "Rich", "The Mets rule" );
		addMessage( "Dom", "The Mets suck" );
	}

	public Widget getPanel() {
		return mainPanel;
	}

	public void addMessage( String sender, String content ) {
		final int rowCount = messageTable.getRowCount();

		messageTable.setText( rowCount, 0, sender );
		messageTable.setText( rowCount, 1, content );

		// Apply cell-level formatting
		messageTable.getCellFormatter().addStyleName( rowCount, 0, "sender-col" );
		messageTable.getCellFormatter().addStyleName( rowCount, 1, "content-col" );

		// Apply row-level formatting
		messageTable.getRowFormatter().addStyleName( rowCount, (rowCount % 2 == 0 ? "row-even" : "row-odd" ) );
	}

	// void addMessage( Message message ) {
	// messages.add( message );
	// }
	//
	// void updateMessage( int messageId ) {
	//
	// }
}
