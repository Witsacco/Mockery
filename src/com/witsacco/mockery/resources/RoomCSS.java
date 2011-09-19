package com.witsacco.mockery.resources;

import com.google.gwt.resources.client.CssResource;

public interface RoomCSS extends CssResource {

	@ClassName( "room-panel" )
	String roomPanel();

	@ClassName( "message-table" )
	String messageTable();

	@ClassName( "row-even" )
	String rowEven();

	@ClassName( "row-odd" )
	String rowOdd();

	@ClassName( "sender-col" )
	String senderCol();

	@ClassName( "content-col" )
	String contentCol();

	@ClassName( "score-col" )
	String scoreCol();

	@ClassName( "score-reason-col" )
	String scoreReasonCol();

	@ClassName( "first-row" )
	String firstRow();

	String spinner();

}