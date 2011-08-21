package com.witsacco.mockery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.witsacco.mockery.events.MessagePostedEvent;
import com.witsacco.mockery.events.MessagePostedEventHandler;
import com.witsacco.mockery.events.MessageScoredEvent;
import com.witsacco.mockery.events.MessageScoredEventHandler;
import com.witsacco.mockery.services.MessagePostedService;
import com.witsacco.mockery.services.MessagePostedServiceAsync;
import com.witsacco.mockery.services.MessageScoreService;
import com.witsacco.mockery.services.MessageScoreServiceAsync;

public class MessagePoster implements AsyncCallback< DisplayMessage >, HasHandlers {

	private MessagePostedServiceAsync messagePostedSvc;
	private MessageScoreServiceAsync scoringSvc;
	
	private MessageScoredHandler messageScoredHandler;

	// GWT event handler manager
	private HandlerManager handlerManager;

	private int activeRoom;
	
	public MessagePoster( int roomId ) {
		
		activeRoom = roomId;
		
		messagePostedSvc = GWT.create( MessagePostedService.class );
		scoringSvc = GWT.create( MessageScoreService.class );
		
		messageScoredHandler = new MessageScoredHandler();
		
		handlerManager = new HandlerManager( this );
	}
	
	public void postMessage( String message ) {
		
		// Call the MessagePostedService
		messagePostedSvc.postMessage( activeRoom, message, this );
	}

	@Override
	public void onFailure( Throwable caught ) {
		Window.alert( "Something went wrong!" );
	}

	@Override
	public void onSuccess( DisplayMessage result ) {
		// Score the new message
		scoringSvc.scoreMessage( activeRoom, result, messageScoredHandler );

		fireEvent( new MessagePostedEvent( result ) );
	}
	
	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	public HandlerRegistration addMessageScoredEventHandler( MessageScoredEventHandler handler ) {
		return handlerManager.addHandler( MessageScoredEvent.TYPE, handler );
	}

	public HandlerRegistration addMessagePostedEventHandler( MessagePostedEventHandler handler ) {
		return handlerManager.addHandler( MessagePostedEvent.TYPE, handler );
	}

	private class MessageScoredHandler implements AsyncCallback< DisplayMessage > {

		public void onFailure( Throwable caught ) {
			Window.alert( "Something went wrong in the scoring service" );
		}

		public void onSuccess( DisplayMessage message ) {
			
			fireEvent( new MessageScoredEvent( message ) );
		}
	}
}
