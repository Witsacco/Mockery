package com.witsacco.mockery.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.witsacco.mockery.events.InitialLoadEvent;
import com.witsacco.mockery.events.InitialLoadEventHandler;
import com.witsacco.mockery.resources.MockeryCSS;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.services.GetUpdatesService;
import com.witsacco.mockery.services.GetUpdatesServiceAsync;

public class LoadingPanel extends VerticalPanel implements AsyncCallback< UpdatePackage >, HasHandlers {

	// Call the updates service
	GetUpdatesServiceAsync initRoomService;

	// GWT event handler manager
	private HandlerManager handlerManager;

	private MockeryCSS css = MockeryResources.INSTANCE.mockeryCss();

	public LoadingPanel() {
		// Invoke superclass constructor
		super();

		handlerManager = new HandlerManager( this );

		// Create room initialization service
		initRoomService = GWT.create( GetUpdatesService.class );

		// Setup the contents of this panel
		initializeUI();
	}

	public void getUpdates( int roomId ) {
		// Load up the initial data
		initRoomService.getUpdates( roomId, new Date(), this );
	}

	private void initializeUI() {

		Image spinner = new Image( MockeryResources.INSTANCE.spinner() );
		Label joinLabel = new Label( "Joining room..." );

		addStyleName( css.spinnerPanel() );

		add( spinner );
		add( joinLabel );
	}

	public void onFailure( Throwable error ) {
		// TODO Handle an error on join more elegantly
		Window.alert( error.getMessage() );
	}

	public void onSuccess( UpdatePackage initialState ) {
		fireEvent( new InitialLoadEvent( initialState ) );
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	// Pass along event listener registration to join handler
	public HandlerRegistration addInitialLoadEventHandler( InitialLoadEventHandler handler ) {
		return handlerManager.addHandler( InitialLoadEvent.TYPE, handler );
	}
}
