package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mockery implements EntryPoint, MessagePostedEventHandler, NewMessagesAvailableEventHandler {

	// An object that represents the current user, logged in or not
	private MockeryUser user = null;

	// For right now, we only have one room
	private final int ROOM_ID = 1;

	Scoreboard scoreboard;
	Room room;
	InputField inputField;
	MessagePoller poller;

	private MessagePostedServiceAsync messagePostedSvc;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Create an instance of the login service
		LoginServiceAsync loginService = GWT.create( LoginService.class );

		// Attempt to log the user in
		loginService.login( GWT.getHostPageBaseURL(), new LoginHandler() );
	}

	private void showLoginScreen() {

		// Panel for the login screen
		FlowPanel loginPanel = new FlowPanel();
		loginPanel.addStyleName( "login-panel" );

		// Labels for the login screen
		Label loginHeader = new Label( "Welcome to Mockery, a-hole." );
		Label loginInstructions = new Label( "To get started, sign in with your Google account." );
		Label loginChallenge = new Label( "Be prepared to bring your A game." );

		// Login button and panel
		Anchor loginButton = new Anchor( "Sign in with Google" );
		loginButton.addStyleName( "login-button" );
		loginButton.setHref( user.getLoginUrl() );
		FlowPanel loginButtonPanel = new FlowPanel();
		loginButtonPanel.add( loginButton );

		// Assemble the panel
		loginPanel.add( loginHeader );
		loginPanel.add( loginInstructions );
		loginPanel.add( loginButtonPanel );
		loginPanel.add( loginChallenge );

		// Add the login panel to the root page element
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add( loginPanel );
	}

	/**
	 * Sets up listeners for new messages posted by the user or new messages
	 * posted by other users
	 */
	private void initializeHandlers() {

		// Add listener on input field for new messages
		inputField.addMessageReceivedEventHandler( this );

		// Add listener on poller for incoming messages
		poller.addNewMessagesAvailableEventHandler( this );
	}

	private void initializeUI() {

		// Create a panel to span the page and align its contents in the center
		HorizontalPanel pagePanel = new HorizontalPanel();
		pagePanel.addStyleName( "page-panel" );
		pagePanel.setHorizontalAlignment( HorizontalPanel.ALIGN_CENTER );

		// Set up the main container to hold the scoreboard, room and entry
		DockLayoutPanel mainDock = new DockLayoutPanel( Unit.PCT );
		mainDock.addStyleName( "main-dock" );

		// Create a header
		Label mainTitleLabel = new Label( "Mockery" );
		mainTitleLabel.addStyleDependentName( "main-title" );

		// Create a logout link
		Anchor logoutButton = new Anchor( "Sign out" );
		logoutButton.addStyleDependentName( "logout-button" );
		logoutButton.setHref( user.getLogoutUrl() );

		// Create the global header panel
		HorizontalPanel header = new HorizontalPanel();
		header.addStyleName( "header" );
		header.setSize( "100%", "100%" );

		// Add main title label left aligned
		header.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		header.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_LEFT );
		header.add( mainTitleLabel );

		// Add "logout" link right aligned
		header.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		header.add( logoutButton );

		// Add the Header to the main UI
		mainDock.addNorth( header, 5 );

		// Add the Input to the main UI
		Widget inputPanel = inputField.getPanel();
		mainDock.addSouth( inputPanel, 15 );

		// Add the Room to the main UI
		Widget roomPanel = room.getPanel();
		mainDock.addWest( roomPanel, 65 );

		// Add the Scoreboard to the main UI
		Widget scoreboardPanel = scoreboard.getPanel();
		mainDock.add( scoreboardPanel );

		// Add the main dock panel to the page panel
		pagePanel.add( mainDock );

		// Add the page panel to the root page element
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add( pagePanel );
	}

	public void updateMessage() {

	}

	@Override
	public void onMessageReceived( MessagePostedEvent event ) {

		// Message newMessage = new Message( event.getMessage(), 1, user );
		// Message newMessage = new Message( event.getMessage(), 1, user );

		// Initialize the service proxy.
		if ( messagePostedSvc == null ) {
			messagePostedSvc = GWT.create( MessagePostedService.class );
		}

		// Set up the callback object.
		AsyncCallback< DisplayMessage > callback = new AsyncCallback< DisplayMessage >() {
			public void onFailure( Throwable caught ) {
				Window.alert( "Something went wrong!" );
			}

			public void onSuccess( DisplayMessage result ) {

				// Score the new message
				// TODO Clean this up!
				MessageScoreServiceAsync scoringSvc = GWT.create( MessageScoreService.class );
				scoringSvc.scoreMessage( ROOM_ID, result.getMessageId(), new AsyncCallback< MessageScore >() {
					public void onFailure( Throwable caught ) {
						Window.alert( "Something went wrong in the scoring service" );
					}

					public void onSuccess( MessageScore score ) {
						// Do something.
					}
				} );

				// Add the message to the room
				room.addMessage( result );
			}
		};

		// Call the MessagePostedService
		messagePostedSvc.postMessage( ROOM_ID, event.getMessage(), callback );
	}

	@Override
	public void onNewMessagesAvailable( NewMessagesAvailableEvent event ) {
		for ( DisplayMessage message : event.getNewMessages() ) {
			room.addMessage( message );
		}
	}

	/**
	 * Private inner class which implements the callback for logging in. On
	 * successful login, this will initialize Mockery for use
	 */
	private class LoginHandler implements AsyncCallback< MockeryUser > {

		public void onFailure( Throwable error ) {
			// TODO Handle an error on login more elegantly
			Window.alert( error.getMessage() );
		}

		public void onSuccess( MockeryUser result ) {
			user = result;

			if ( user.isLoggedIn() ) {

				// Create UI elements
				scoreboard = new Scoreboard();
				room = new Room();
				inputField = new InputField();
				poller = new MessagePoller( ROOM_ID );

				// Add the user to the scoreboard
				// TODO Fix this
				scoreboard.addUser( user.getNickname(), 0 );

				// Set up user interface
				initializeUI();

				// Set up listeners for events
				initializeHandlers();

				// Start polling for new messages
				poller.startPolling();
			}
			else {
				showLoginScreen();
			}
		}
	}
}
