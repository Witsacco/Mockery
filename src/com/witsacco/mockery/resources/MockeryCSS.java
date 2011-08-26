package com.witsacco.mockery.resources;

import com.google.gwt.resources.client.CssResource;

public interface MockeryCSS extends CssResource {

	@ClassName( "window-pane")
	String windowPane();
	
	@ClassName( "main-dock")
	String mainDock();
	
	@ClassName("header")
	String header();

	@ClassName("spinner-panel")
	String spinnerPanel();

	String sheep();
}
