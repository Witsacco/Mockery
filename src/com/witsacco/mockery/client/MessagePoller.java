package com.witsacco.mockery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MessagePoller extends Timer implements HasHandlers {

	static final int POLL_INTERVAL = 5000;

	private GetNewMessagesServiceAsync getNewMessagesSvc;

	private HandlerManager handlerManager;

	public MessagePoller() {
		getNewMessagesSvc = GWT.create( GetNewMessagesService.class );
		handlerManager = new HandlerManager( this );
	}

	public void startPolling() {
		scheduleRepeating( POLL_INTERVAL );
	}

	@Override
	public void run() {

		// Set up the callback object.
		AsyncCallback< Void > callback = new AsyncCallback< Void >() {
			public void onFailure( Throwable caught ) {
				Window.alert( "Something went wrong!" );
			}

			public void onSuccess( Void res ) {
				fireEvent( new NewMessagesAvailableEvent() );
			}
		};

		// Make the call to the stock price service.
		getNewMessagesSvc.getNewMessages( callback );
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	public HandlerRegistration addNewMessagesAvailableEventHandler(
			NewMessagesAvailableEventHandler handler ) {
		return handlerManager.addHandler( NewMessagesAvailableEvent.TYPE,
				handler );
	}
}
