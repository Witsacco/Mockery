package com.witsacco.mockery.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MockeryResources extends ClientBundle {

	public static final MockeryResources INSTANCE = GWT.create( MockeryResources.class );

	@Source( "sheep.jpg" )
	ImageResource sheep();

	@Source( "Mockery.css" )
	public MockeryCSS mockeryCss();

	@Source( "Scoreboard.css" )
	public ScoreboardCSS scoreboardCss();

	@Source( "Room.css" )
	public RoomCSS roomCss();

	@Source( "InputField.css" )
	public InputFieldCSS inputFieldCss();

	@Source( "Login.css" )
	public LoginCSS loginCss();

	@Source( "spinner.gif" )
	ImageResource spinner();
}
