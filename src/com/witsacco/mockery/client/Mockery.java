package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mockery implements EntryPoint {

	Scoreboard scoreboard;
	Room room;
//	MockeryUser user;
	InputField inputField;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
 		scoreboard = new Scoreboard();
 		room = new Room();
 		//TODO get mockery user
 		inputField = new InputField();

 		initializeUI();
	}

	private void initializeUI() {
		
	    DockLayoutPanel mainPanel = new DockLayoutPanel( Unit.EM );
	    mainPanel.setStyleName( "mainPanel" );
	    mainPanel.setSize( "670px", "550px" );
	    
	    HorizontalPanel header = new HorizontalPanel();
	    header.setStyleName( "header" );
	    Label headerLabel = new Label( "Mockery" );
	    headerLabel.setStyleName( "headerPanel" );
	    header.add( headerLabel );
	    headerLabel.setWidth( "100%" );
	    
	    mainPanel.addNorth( header, 2 );
	    header.setSize( "100%", "100%" );
	    
	    HTML html_1 = new HTML("Text entry");
	    html_1.setStyleName("entry-panel");
	    mainPanel.addSouth( html_1, 5.8 );

	    HTML scoreboardPanel = new HTML("Scoreboard");
	    scoreboardPanel.setStyleName("scoreboard-panel");
	    mainPanel.addEast( scoreboardPanel, 20.5 );

	    HTML roomPanel = new HTML("Room");
	    roomPanel.setStyleName("room-panel");
	    mainPanel.addWest( roomPanel, 30.6 );

	    RootLayoutPanel rp = RootLayoutPanel.get();
	    rp.add( mainPanel );
	    rp.setWidgetLeftWidth( mainPanel, 61.0, Unit.PX, 723.0, Unit.PX );
	    rp.setWidgetTopHeight( mainPanel, 39.0, Unit.PX, 603.0, Unit.PX );
	}
	
	public void createMessage() {
		
	}
	
	
//	public void createMessageCallback() {
//		room.addMessage();
//		room.redraw();
//	}
		
	public void updateMessage() {
		
	}

//	public void updateMessageCallback() {
//		room.updateMessage();
//		scoreboard.getStanding();
//		scoreboard.redraw();
//	}
}
