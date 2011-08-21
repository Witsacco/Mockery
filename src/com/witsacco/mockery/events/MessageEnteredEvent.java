package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;

public class MessageEnteredEvent extends GwtEvent< MessageEnteredEventHandler > {

	public static Type< MessageEnteredEventHandler > TYPE = new Type< MessageEnteredEventHandler >();

	private final String message;

	public MessageEnteredEvent( String message ) {
		this.message = message;
	}

	@Override
	public Type< MessageEnteredEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( MessageEnteredEventHandler handler ) {
		handler.onMessageEntered( this );
	}

	public String getMessage() {
		return message;
	}
}
