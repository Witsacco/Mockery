package com.witsacco.mockery.resources;

import com.google.gwt.resources.client.CssResource;

public interface MockeryCSS extends CssResource {

	@ClassName( "page-panel" )
	String pagePanel();

	@ClassName( "main-dock")
	String mainDock();
	
	@ClassName("header")
	String header();

	String sheep();
}
