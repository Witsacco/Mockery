package com.witsacco.mockery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.witsacco.mockery.events.UserJoinedEvent;
import com.witsacco.mockery.events.UserJoinedEventHandler;
import com.witsacco.mockery.resources.JoinCSS;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.services.JoinService;
import com.witsacco.mockery.services.JoinServiceAsync;

public class JoinScreen extends VerticalPanel implements ClickHandler, HasHandlers {

	// Handle to bundled CSS resources
	private JoinCSS css = MockeryResources.INSTANCE.joinCss();

	private static final String JOIN_MESSAGE = "This obviously needs style, Drywit.";

	// Service for joining a room
	private JoinServiceAsync joinService;

	// Handler for joining room events/callbacks
	private JoinHandler joinHandler;

	// GWT event handler manager
	private HandlerManager handlerManager;

	private ListBox roomSelector;
	private TextBox userHandleInput;
	private Button joinButton;
	private int activeRoom;

	public JoinScreen() {
		// Invoke constructor of super class (VerticalPanel)
		super();

		// Set up a new join service
		joinService = GWT.create( JoinService.class );

		// Initialize handler for join events/callbacks
		joinHandler = new JoinHandler();

		// Initialize GWT event handler manager
		handlerManager = new HandlerManager( joinHandler );

		// Set up the UI for the Join Panel
		initializeUI();
	}

	// Pass along event listener registration to join handler
	public HandlerRegistration addUserJoinedEventHandler( UserJoinedEventHandler handler ) {
		return handlerManager.addHandler( UserJoinedEvent.TYPE, handler );
	}

	private void initializeUI() {

		// Inject CSS for join screen
		css.ensureInjected();

		// Add styling to the join panel
		addStyleName( css.joinPanel() );

		// Add a welcoming message
		Label welcomeMessage = new Label( JOIN_MESSAGE );
		add( welcomeMessage );

		// Set up the room selection dropdown and add it to the panel
		add( setupRoomSelector() );

		userHandleInput = new TextBox();
		add( userHandleInput );

		joinButton = new Button( "Join Room", this );
		add( joinButton );
	}

	public void onClick( ClickEvent event ) {

		// Get the session's active room
		activeRoom = Integer.parseInt( roomSelector.getValue( roomSelector.getSelectedIndex() ) );

		// Grab the user's requested handle
		String handle = userHandleInput.getText();

		// Register this user
		joinService.join( activeRoom, handle, joinHandler );
	}

	/**
	 * This sub is responsible for grabbing all active rooms. For now, it simply
	 * sets up one room
	 */
	private ListBox setupRoomSelector() {

		// Set up the new ListBox for choosing the desired room
		roomSelector = new ListBox();

		// For now there is only one room
		roomSelector.addItem( "Main Room", "1" );

		return roomSelector;
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	/**
	 * Private inner class which implements the callback for prompting a
	 * logged-in user for a handle. Once the user sets a handle, this will
	 * initialize Mockery for use.
	 */
	private class JoinHandler implements AsyncCallback< DisplayUser > {

		public void onFailure( Throwable error ) {
			// TODO Handle an error on join more elegantly
			Window.alert( error.getMessage() );
		}

		public void onSuccess( DisplayUser dUser ) {

			// Fire an event notifying Mockery that the user joined the room
			UserJoinedEvent userJoined = new UserJoinedEvent( dUser, activeRoom );
			fireEvent( userJoined );
		}
	}
}
