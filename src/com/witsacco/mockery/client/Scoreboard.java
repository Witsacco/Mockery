package com.witsacco.mockery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.resources.ScoreboardCSS;

public class Scoreboard extends ScrollPanel {

	private FlexTable scoreTable;

	private ScoreboardCSS css = MockeryResources.INSTANCE.scoreboardCss();

	public Scoreboard() {
		// Invoke superclass constructor
		super();

		initializeUI();
	}

	private void initializeUI() {

		css.ensureInjected();

		addStyleName( css.scoreboardPanel() );

		scoreTable = new FlexTable();
		scoreTable.addStyleName( css.scoreTable() );

		resetScoreboard();

		add( scoreTable );
	}

	private void resetScoreboard() {

		scoreTable.removeAllRows();

		// Add header to table
		scoreTable.setText( 0, 0, "User" );
		scoreTable.setText( 0, 1, "Score" );
		scoreTable.getRowFormatter().addStyleName( 0, css.scoreHeader() );
	}

	void addUser( DisplayUser user ) {
		final int rowCount = scoreTable.getRowCount();
		scoreTable.setText( rowCount, 0, user.getHandle() );
		scoreTable.setText( rowCount, 1, user.getCumulativeScore().toString() );

		// Apply cell-level formatting
		scoreTable.getCellFormatter().addStyleName( rowCount, 0, css.userCol() );
		scoreTable.getCellFormatter().addStyleName( rowCount, 1, css.scoreCol() );

		// Add a bullet for the user's online status
		if ( user.isLoggedIn() ) {
			scoreTable.getCellFormatter().addStyleName( rowCount, 0, css.isOnline() );
		}
		else {
			scoreTable.getCellFormatter().addStyleName( rowCount, 0, css.isOffline() );
		}

		// Apply row-level formatting
		scoreTable.getRowFormatter().addStyleName( rowCount, ( rowCount % 2 == 0 ? css.rowEven() : css.rowOdd() ) );
	}

	public void update( ArrayList< DisplayUser > currentStandings ) {

		resetScoreboard();

		for ( DisplayUser u : currentStandings ) {
			addUser( u );
		}

	}
}
