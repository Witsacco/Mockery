package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

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
 		inputField = new InputField();

 		//TODO get mockery user

 		initializeUI();
	}

	private void initializeUI() {

		// Create a panel to span the page and align its contents in the center
		HorizontalPanel pagePanel = new HorizontalPanel();
		pagePanel.addStyleName( "page-panel" );
		pagePanel.setHorizontalAlignment( HorizontalPanel.ALIGN_CENTER );
		
		// Set up the main container to hold the scoreboard, room and entry
		DockLayoutPanel mainDock = new DockLayoutPanel( Unit.PCT );
		mainDock.addStyleName( "main-dock" );

		// Add a Header to the main UI
		Label headerLabel = new Label( "Mockery" );
		headerLabel.addStyleName( "header-label" );
		mainDock.addNorth(  headerLabel, 5 );
		
		// Add the Input to the main UI
		Widget inputPanel = inputField.getPanel();
		mainDock.addSouth( inputPanel, 15 );
		
		// Add the Room to the main UI
		Widget roomPanel = room.getPanel();
		mainDock.addWest( roomPanel, 65 );
		
		// Add the Scoreboard to the main UI
		Widget scoreboardPanel = scoreboard.getPanel();
		mainDock.add( scoreboardPanel );
		
		// Add the main dock panel to the page panel
		pagePanel.add( mainDock );
		
		// Add the page panel to the root page element
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(  pagePanel );
	}
	
	public void createMessage() {
		
	}
	
	
//	public void createMessageCallback() {
//		room.addMessage();
//		room.redraw();
//	}
		
	public void updateMessage() {
		
	}
}
