package com.witsacco.mockery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
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

public class JoinScreen extends DecoratorPanel implements ClickHandler, HasHandlers, KeyPressHandler {

	// Handle to bundled CSS resources
	private JoinCSS css = MockeryResources.INSTANCE.joinCss();

	private static final String JOIN_MESSAGE = "Please choose a screen name:";

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
		handlerManager = new HandlerManager( this );

		// Set up the UI for the Join Panel
		initializeUI();
	}

	public void setFocus() {
		// Set the focus to the input box
		userHandleInput.setFocus( true );
	}
	
	// Pass along event listener registration to join handler
	public HandlerRegistration addUserJoinedEventHandler( UserJoinedEventHandler handler ) {
		return handlerManager.addHandler( UserJoinedEvent.TYPE, handler );
	}

	private void initializeUI() {

		// Inject CSS for join screen
		css.ensureInjected();

		// Add some styling to the main decorator panel
		addStyleName( css.decorator() );

		VerticalPanel mainPanel = new VerticalPanel();
		
		// Add styling to the join panel
		mainPanel.addStyleName( css.joinPanel() );

		// Add a welcoming message
		Label welcomeMessage = new Label( JOIN_MESSAGE );
		welcomeMessage.addStyleName( css.joinMessage() );
		mainPanel.add( welcomeMessage );

		userHandleInput = new TextBox();
		userHandleInput.addKeyPressHandler( this );
		mainPanel.add( userHandleInput );

		// Set up the room selection dropdown and add it to the panel
		mainPanel.add( setupRoomSelector() );

		joinButton = new Button( "Join Room", this );
		joinButton.addStyleName( css.joinButton() );
		mainPanel.add( joinButton );
		
		add( mainPanel );
	}

	public void onClick( ClickEvent event ) {
		join();
	}

	public void onKeyPress( KeyPressEvent event ) {
		// Leave if we didn't see the enter key
		if ( event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER ) {
			join();

			// Cancel enter key
			event.preventDefault();
		}
	}
	
	private void join() {
		
		// Get the session's active room
		activeRoom = Integer.parseInt( roomSelector.getValue( roomSelector.getSelectedIndex() ) );

		// Grab the user's requested handle
		String handle = userHandleInput.getText();
		
		// If no screen name was chosen, just leave
		if ( handle.length() == 0 ) {
			return;
		}

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
		roomSelector.addStyleName( css.roomChooser() );

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
			fireEvent( new UserJoinedEvent( dUser, activeRoom ) );
		}
	}
}
