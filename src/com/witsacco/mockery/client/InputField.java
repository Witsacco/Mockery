package com.witsacco.mockery.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class InputField {

	private FlowPanel mainPanel;
	
	public InputField() {
		
		mainPanel = new FlowPanel();
		mainPanel.addStyleName( "input-panel" );
	}

	public Widget getPanel() {
		return mainPanel;
	}
	
	public String getText() {
		//TODO Grab text from DOM
		return "";
	}
	
	private boolean isValid( String text ) {
		//TODO implement input validation logic (FieldVerifier?)
		return true;
	}
	
}
