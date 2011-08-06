package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Scoreboard {

	private ScrollPanel mainPanel;
	private FlexTable scoreTable;

	// HashSet<MockeryUser> users;

	public Scoreboard() {

		// users = new HashSet< MockeryUser >();
		initializeUI();
	}

	private void initializeUI() {

		// Create a scroll pnel to hold the scoreboard table
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( "scoreboard-panel" );

		scoreTable = new FlexTable();
		scoreTable.addStyleName( "score-table" );

		// Add header to table
		scoreTable.setText( 0, 0, "User" );
		scoreTable.setText( 0, 1, "Score" );
		scoreTable.getRowFormatter().addStyleName( 0, "score-header" );

		addUser( "Rich", 10 );
		addUser( "Ryan", 5 );
		addUser( "Chris", -1 );
		addUser( "Dom", -10 );
		
		mainPanel.add( scoreTable );
	}

	public Widget getPanel() {
		return mainPanel;
	}

	 void addUser( String userName, int score ) {
		 final int rowCount = scoreTable.getRowCount();
		 scoreTable.setText( rowCount, 0, userName );
		 scoreTable.setText( rowCount, 1, Integer.toString( score ) );
		 
		// Apply cell-level formatting
		scoreTable.getCellFormatter().addStyleName( rowCount, 0, "user-col" );
		scoreTable.getCellFormatter().addStyleName( rowCount, 1, "score-col" );

		// Apply row-level formatting
		scoreTable.getRowFormatter().addStyleName( rowCount, (rowCount % 2 == 0 ? "row-even" : "row-odd" ) );
	 }

	 //
	// ArrayList< MockeryUser > getStandings() {
	//
	// }
}
