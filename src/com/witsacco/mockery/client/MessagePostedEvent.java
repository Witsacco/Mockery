package com.witsacco.mockery.client;

import com.google.gwt.event.shared.GwtEvent;

public class MessagePostedEvent extends GwtEvent< MessagePostedEventHandler > {

	public static Type< MessagePostedEventHandler > TYPE = new Type< MessagePostedEventHandler >();

	private final String message;

	public MessagePostedEvent( String message ) {
		this.message = message;
	}

	@Override
	public Type< MessagePostedEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( MessagePostedEventHandler handler ) {
		handler.onMessageReceived( this );
	}

	public String getMessage() {
		return message;
	}
}
