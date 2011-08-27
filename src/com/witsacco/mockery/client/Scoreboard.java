package com.witsacco.mockery.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.resources.ScoreboardCSS;

public class Scoreboard extends ScrollPanel {

	private FlexTable scoreTable;

	private ScoreboardCSS css = MockeryResources.INSTANCE.scoreboardCss();
	
	ArrayList< DisplayUser > priorStandings;

	public Scoreboard() {
		// Invoke superclass constructor
		super();

		// Initialize the reference to the prior standings
		priorStandings = new ArrayList< DisplayUser >();
		
		initializeUI();
	}

	private void initializeUI() {

		css.ensureInjected();

		addStyleName( css.scoreboardPanel() );

		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
		mainPanel.setWidth( "100%" );

		scoreTable = new FlexTable();
		scoreTable.addStyleName( css.scoreTable() );
		mainPanel.add( scoreTable );

		add( mainPanel );

		// Initialize the header on table
		resetScoreboard();
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
		} else {
			scoreTable.getCellFormatter().addStyleName( rowCount, 0, css.isOffline() );
		}

		// Apply row-level formatting
		scoreTable.getRowFormatter().addStyleName( rowCount, ( rowCount % 2 == 0 ? css.rowEven() : css.rowOdd() ) );
	}

	public void update( ArrayList< DisplayUser > newStandings ) {

		if ( Arrays.equals( priorStandings.toArray(), newStandings.toArray() ) ) {
			return;
		}
		
		resetScoreboard();

		for ( DisplayUser u : newStandings ) {
			addUser( u );
		}

		priorStandings = newStandings;
	}
}
