package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.DisplayMessage;

public class MessagePostedEvent extends GwtEvent< MessagePostedEventHandler > {

	public static Type< MessagePostedEventHandler > TYPE = new Type< MessagePostedEventHandler >();

	private DisplayMessage displayMessage;
	
	public MessagePostedEvent( DisplayMessage message ) {
		displayMessage = message;
	}

	@Override
	public Type< MessagePostedEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( MessagePostedEventHandler handler ) {
		handler.onMessagePosted( this );
	}
	
	public DisplayMessage getMessage() {
		return displayMessage;
	}
}
