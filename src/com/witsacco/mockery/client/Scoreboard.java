package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Scoreboard {

	private ScrollPanel mainPanel;
	
//	HashSet<MockeryUser> users;

	public Scoreboard() {
		
		mainPanel = new ScrollPanel();
		mainPanel.addStyleName( "scoreboard-panel" );
		
//		users = new HashSet< MockeryUser >();
	}
	
	public Widget getPanel() {
		return mainPanel;
	}
	
//	void addUser( MockerUser newUser )  {
//		
//	}
//	
//	ArrayList< MockeryUser > getStandings() {
//		
//	}
}
