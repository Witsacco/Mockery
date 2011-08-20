package com.witsacco.mockery.resources;

import com.google.gwt.resources.client.CssResource;

public interface ScoreboardCSS extends CssResource {

	@ClassName( "scoreboard-panel" )
	String scoreboardPanel();

	@ClassName( "score-table" )
	String scoreTable();

	@ClassName( "score-header" )
	String scoreHeader();

	@ClassName( "row-even" )
	String rowEven();

	@ClassName( "row-odd" )
	String rowOdd();

	@ClassName( "user-col" )
	String userCol();

	@ClassName( "score-col" )
	String scoreCol();

	@ClassName( "online" )
	String isOnline();

	@ClassName( "offline" )
	String isOffline();
	
}
