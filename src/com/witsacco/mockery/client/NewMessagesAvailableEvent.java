package com.witsacco.mockery.client;

import com.google.gwt.event.shared.GwtEvent;

public class NewMessagesAvailableEvent extends
		GwtEvent< NewMessagesAvailableEventHandler > {

	public static Type< NewMessagesAvailableEventHandler > TYPE = new Type< NewMessagesAvailableEventHandler >();

	public NewMessagesAvailableEvent() {
	}

	@Override
	public Type< NewMessagesAvailableEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( NewMessagesAvailableEventHandler handler ) {
		handler.onNewMessagesAvailable( this );
	}
}
