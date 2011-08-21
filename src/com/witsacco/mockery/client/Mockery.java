package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.witsacco.mockery.events.InitialLoadEvent;
import com.witsacco.mockery.events.InitialLoadEventHandler;
import com.witsacco.mockery.events.MessageEnteredEvent;
import com.witsacco.mockery.events.MessageEnteredEventHandler;
import com.witsacco.mockery.events.MessagePostedEvent;
import com.witsacco.mockery.events.MessagePostedEventHandler;
import com.witsacco.mockery.events.MessageScoredEvent;
import com.witsacco.mockery.events.MessageScoredEventHandler;
import com.witsacco.mockery.events.NewMessagesAvailableEvent;
import com.witsacco.mockery.events.NewMessagesAvailableEventHandler;
import com.witsacco.mockery.events.ScoreboardUpdateAvailableEvent;
import com.witsacco.mockery.events.ScoreboardUpdateAvailableEventHandler;
import com.witsacco.mockery.events.UserJoinedEvent;
import com.witsacco.mockery.events.UserJoinedEventHandler;
import com.witsacco.mockery.events.UserLoginEvent;
import com.witsacco.mockery.events.UserLoginEventHandler;
import com.witsacco.mockery.resources.MockeryCSS;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.services.LeaveService;
import com.witsacco.mockery.services.LeaveServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mockery implements EntryPoint {

	// An object that represents the current log in info/status
	private LoginInfo loginInfo;

	// The room that the user is in
	private int activeRoom;

	// Our update poller
	private UpdatePoller poller;

	private MockeryEventManager eventManager;
	
	// Class for posting and scoring new messages
	private MessagePoster messagePoster;

	// UI Elements
	private Scoreboard scoreboard;
	private Room room;
	private InputField inputField;

	private LoginScreen loginScreen;

	private MockeryCSS css = MockeryResources.INSTANCE.mockeryCss();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		eventManager = new MockeryEventManager();

		loginScreen = new LoginScreen();

		loginScreen.addUserLoginEventHandler( eventManager );

		// Attempt to log the user in
		loginScreen.login();
	}

	private void showLoginScreen() {
		// Add the login panel to the root page element
		RootLayoutPanel.get().add( loginScreen );
	}

	private void showJoinScreen() {

		JoinScreen joinScreen = new JoinScreen();

		joinScreen.addUserJoinedEventHandler( eventManager );

		// Add join screen
		RootLayoutPanel.get().add( joinScreen );
	}

	private void showLoadingScreen() {

		// Clear the panel
		RootLayoutPanel.get().clear();

		// Create a new loading panel
		LoadingPanel loadingPanel = new LoadingPanel();
		
		// Register the eventManager as a listener
		loadingPanel.addInitialLoadEventHandler( eventManager );
		
		// Add the loading panel to the page
		RootLayoutPanel.get().add( loadingPanel );

		// Call the updates service
		loadingPanel.getUpdates( activeRoom );
	}

	/**
	 * Sets up listeners for new messages posted by the user or new messages
	 * posted by other users
	 */
	private void initializeHandlers() {

		// Initialize the message posting and scoring service
		messagePoster = new MessagePoster( activeRoom );

		// Add listeners for message posted and message scored events
		messagePoster.addMessagePostedEventHandler( eventManager );
		messagePoster.addMessageScoredEventHandler( eventManager );
		
		// Add listener on input field for new messages
		inputField.addMessageReceivedEventHandler( eventManager );

		// Add listener on poller for incoming messages
		poller.addNewMessagesAvailableEventHandler( eventManager );

		// Add listener on poller for standings updates
		poller.addScoreboardUpdateAvailableEventHandler( eventManager );

		// Add listener for the window close event to log the user out
		Window.addWindowClosingHandler( eventManager );
	}

	private void initializeUI() {

		// Create a panel to span the page and align its contents in the center
		HorizontalPanel pagePanel = new HorizontalPanel();
		pagePanel.addStyleName( css.pagePanel() );
		pagePanel.addStyleName( css.sheep() );
		pagePanel.setHorizontalAlignment( HorizontalPanel.ALIGN_CENTER );

		// Set up the main container to hold the scoreboard, room and entry
		DockLayoutPanel mainDock = new DockLayoutPanel( Unit.PCT );
		mainDock.addStyleName( css.mainDock() );

		// Create a header
		Label mainTitleLabel = new Label( "Mockery" );
		mainTitleLabel.addStyleDependentName( "main-title" );

		// Create a logout link
		Anchor logoutButton = new Anchor( "Sign out" );
		logoutButton.addStyleDependentName( "logout-button" );
		logoutButton.setHref( loginInfo.getLogoutUrl() );

		// Create the global header panel
		HorizontalPanel header = new HorizontalPanel();
		header.addStyleName( css.header() );
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


	/*
	 * Private inner class which goes back to the join screen after leaving
	 */
	private class LeaveHandler implements AsyncCallback< Void > {

		public void onFailure( Throwable error ) {
			// TODO Handle an error on join more elegantly
		}

		public void onSuccess( Void nothing ) {
			if ( loginInfo.isLoggedIn() ) {
				showJoinScreen();
			} else {
				showLoginScreen();
			}

		}
	}

	/**
	 * Class for handling mockery-related events
	 * 
	 */
	private class MockeryEventManager implements UserLoginEventHandler, UserJoinedEventHandler,
			MessageEnteredEventHandler, NewMessagesAvailableEventHandler, ScoreboardUpdateAvailableEventHandler,
			Window.ClosingHandler, InitialLoadEventHandler, MessagePostedEventHandler, MessageScoredEventHandler {

		@Override
		public void onUserLogin( UserLoginEvent event ) {
			loginInfo = event.getLoginInfo();

			if ( loginInfo.isLoggedIn() ) {
				showJoinScreen();
			} else {
				showLoginScreen();
			}
		}

		@Override
		public void onUserJoined( UserJoinedEvent event ) {
			// Add the appropriate Mockery CSS
			css.ensureInjected();

			activeRoom = event.getRoomId();

			// Create UI elements
			room = new Room();
			scoreboard = new Scoreboard();
			inputField = new InputField();
			poller = new UpdatePoller( activeRoom );

			// Show the loading screen
			showLoadingScreen();
		}

		@Override
		public void onInitialLoad( InitialLoadEvent event ) {

			// Grrab the initial state
			UpdatePackage initialState = event.getInitialState();
			
			// Clear the panel
			RootLayoutPanel.get().clear();

			// Draw the room
			initializeUI();

			// Set up listeners for events
			initializeHandlers();

			// Set the scoreboard
			scoreboard.update( initialState.getCurrentStandings() );

			// Start polling for new messages
			poller.startPolling();
		}

		@Override
		public void onMessageEntered( MessageEnteredEvent event ) {
			messagePoster.postMessage( event.getMessage() );
		}

		@Override
		public void onNewMessagesAvailable( NewMessagesAvailableEvent event ) {
			for ( DisplayMessage message : event.getNewMessages() ) {
				room.handleIncomingMessage( message );
			}
		}

		@Override
		public void onScoreboardUpdateAvailable( ScoreboardUpdateAvailableEvent event ) {
			// Update the scoreboard with the current standings
			scoreboard.update( event.getCurrentStandings() );
		}

		@Override
		public void onMessageScored( MessageScoredEvent event ) {
			// Update the room with the now-scored message
			room.handleIncomingMessage( event.getScoredMessage() );
		}

		@Override
		public void onMessagePosted( MessagePostedEvent event ) {
			// Add the message to the room
			room.addMessage( event.getMessage() );
		}
		
		@Override
		public void onWindowClosing( ClosingEvent event ) {
			// The user is leaving, log them out
			LeaveServiceAsync leaveService = GWT.create( LeaveService.class );
			leaveService.leave( activeRoom, new LeaveHandler() );
		}
	}
}
