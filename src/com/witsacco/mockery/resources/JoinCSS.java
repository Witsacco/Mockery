package com.witsacco.mockery.resources;

import com.google.gwt.resources.client.CssResource;

public interface JoinCSS extends CssResource {
	@ClassName( "join-panel" )
	String joinPanel();

	String decorator();

	@ClassName( "join-message" )
	String joinMessage();

	@ClassName( "join-button" )
	String joinButton();

	@ClassName( "room-chooser" )
	String roomChooser();
}
