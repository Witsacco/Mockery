package com.witsacco.mockery.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UpdatePoller extends Timer implements HasHandlers {

	// Poll for new messages every three seconds
	private static final int POLL_INTERVAL = 3000;

	private int roomId;

	private Date lastChecked;

	private GetUpdatesServiceAsync getNewMessagesSvc;
	private HandlerManager handlerManager;

	public UpdatePoller( int roomId ) {
		this.roomId = roomId;

		getNewMessagesSvc = GWT.create( GetUpdatesService.class );
		handlerManager = new HandlerManager( this );

		// Initialize the last time we checked for new messages to now
		lastChecked = new Date();
	}

	public void startPolling() {
		scheduleRepeating( POLL_INTERVAL );
	}

	@Override
	public void run() {

		// Set up the callback object.
		AsyncCallback< UpdatePackage > callback = new AsyncCallback< UpdatePackage >() {
			public void onFailure( Throwable caught ) {
				Window.alert( "Something went wrong!" );
			}

			// Handler for successful retrieval of new messages
			public void onSuccess( UpdatePackage updates ) {

				ArrayList< DisplayMessage > res = updates.getMessages();

				// If we found new messages, fire and event for listeners to
				// process
				if ( res.size() > 0 ) {
					fireEvent( new NewMessagesAvailableEvent( res ) );
				}

				// Update the last time we checked for the next iteration
				lastChecked = new Date();
			}
		};

		// Make the call to the stock price service.
		getNewMessagesSvc.getUpdates( roomId, lastChecked, callback );
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	// Allow listeners to register themselves as listeners for events
	public HandlerRegistration addNewMessagesAvailableEventHandler( NewMessagesAvailableEventHandler handler ) {
		return handlerManager.addHandler( NewMessagesAvailableEvent.TYPE, handler );
	}
}
