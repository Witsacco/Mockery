package com.witsacco.mockery.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.DisplayMessage;

public class NewMessagesAvailableEvent extends GwtEvent< NewMessagesAvailableEventHandler > {

	public static Type< NewMessagesAvailableEventHandler > TYPE = new Type< NewMessagesAvailableEventHandler >();

	private ArrayList< DisplayMessage > messages;

	public NewMessagesAvailableEvent( ArrayList< DisplayMessage > newOnes ) {
		messages = newOnes;
	}

	@Override
	public Type< NewMessagesAvailableEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( NewMessagesAvailableEventHandler handler ) {
		handler.onNewMessagesAvailable( this );
	}

	public ArrayList< DisplayMessage > getNewMessages() {
		return messages;
	}
}
