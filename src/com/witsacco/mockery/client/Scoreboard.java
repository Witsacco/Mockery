package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.resources.ScoreboardCSS;

public class Scoreboard {

	private ScrollPanel mainPanel;
	private FlexTable scoreTable;

	private ScoreboardCSS css = MockeryResources.INSTANCE.scoreboardCss();

	// HashSet<MockeryUser> users;

	public Scoreboard() {

		// users = new HashSet< MockeryUser >();
		initializeUI();
	}

	private void initializeUI() {

		css.ensureInjected();

		// Create a scroll pnel to hold the scoreboard table
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( css.scoreboardPanel() );

		scoreTable = new FlexTable();
		scoreTable.addStyleName( css.scoreTable() );

		// Add header to table
		scoreTable.setText( 0, 0, "User" );
		scoreTable.setText( 0, 1, "Score" );
		scoreTable.getRowFormatter().addStyleName( 0, css.scoreHeader() );

		addUser( "Rich", new Long( 10 ) );
		addUser( "Ryan", new Long( 5 ) );
		addUser( "Chris", new Long( -1 ) );
		addUser( "Dom", new Long( -10 ) );

		mainPanel.add( scoreTable );
	}

	public Widget getPanel() {
		return mainPanel;
	}

	void addUser( String userName, Long score ) {
		final int rowCount = scoreTable.getRowCount();
		scoreTable.setText( rowCount, 0, userName );
		scoreTable.setText( rowCount, 1, score.toString() );

		// Apply cell-level formatting
		scoreTable.getCellFormatter().addStyleName( rowCount, 0, css.userCol() );
		scoreTable.getCellFormatter().addStyleName( rowCount, 1, css.scoreCol() );

		// Apply row-level formatting
		scoreTable.getRowFormatter().addStyleName( rowCount, ( rowCount % 2 == 0 ? css.rowEven() : css.rowOdd() ) );
	}

	//
	// ArrayList< MockeryUser > getStandings() {
	//
	// }
}
