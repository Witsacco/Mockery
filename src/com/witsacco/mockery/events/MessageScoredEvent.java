package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.DisplayMessage;

public class MessageScoredEvent extends GwtEvent< MessageScoredEventHandler > {

	public static Type< MessageScoredEventHandler > TYPE = new Type< MessageScoredEventHandler >();

	private DisplayMessage scoredMessage;
	
	public MessageScoredEvent( DisplayMessage message ) {
		scoredMessage = message;
	}

	@Override
	public Type< MessageScoredEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( MessageScoredEventHandler handler ) {
		handler.onMessageScored( this );
	}
	
	public DisplayMessage getScoredMessage() {
		return scoredMessage;
	}
}
