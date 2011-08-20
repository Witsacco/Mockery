package com.witsacco.mockery.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.witsacco.mockery.events.MessagePostedEvent;
import com.witsacco.mockery.events.MessagePostedEventHandler;
import com.witsacco.mockery.events.NewMessagesAvailableEvent;
import com.witsacco.mockery.events.NewMessagesAvailableEventHandler;
import com.witsacco.mockery.resources.LoginCSS;
import com.witsacco.mockery.resources.MockeryCSS;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.services.JoinService;
import com.witsacco.mockery.services.JoinServiceAsync;
import com.witsacco.mockery.services.LoginService;
import com.witsacco.mockery.services.LoginServiceAsync;
import com.witsacco.mockery.services.MessagePostedService;
import com.witsacco.mockery.services.MessagePostedServiceAsync;
import com.witsacco.mockery.services.MessageScoreService;
import com.witsacco.mockery.services.MessageScoreServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mockery implements EntryPoint, MessagePostedEventHandler, NewMessagesAvailableEventHandler {

	// An object that represents the current log in info/status
	private LoginInfo loginInfo = null;

	// The user's information (handle, score, etc.)
	private DisplayUser user = null;

	// The room that the user is in
	private int activeRoom;

	// Our update poller
	private UpdatePoller poller;

	// UI Elements
	private Scoreboard scoreboard;
	private Room room;
	private InputField inputField;

	private MessagePostedServiceAsync messagePostedSvc;

	private MockeryCSS css = MockeryResources.INSTANCE.mockeryCss();
	private LoginCSS loginCss = MockeryResources.INSTANCE.loginCss();

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

		loginCss.ensureInjected();
		
		// Panel for the login screen
		FlowPanel loginPanel = new FlowPanel();
		loginPanel.addStyleName( loginCss.loginPanel() );

		// Labels for the login screen
		Label loginHeader = new Label( "Welcome to Mockery, a-hole." );
		Label loginInstructions = new Label( "To get started, sign in with your Google account." );
		Label loginChallenge = new Label( "Be prepared to bring your A game." );

		// Login button and panel
		Anchor loginButton = new Anchor( "Sign in with Google" );
		loginButton.addStyleName( "login-button" );
		loginButton.setHref( loginInfo.getLoginUrl() );
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

	private void showJoinScreen() {

		// Make a panel to contain the controls
		VerticalPanel joinPanel = new VerticalPanel();
		joinPanel.setWidth( "300px" );

		// TODO Unneeded obviously, remove this
		final Label disclaimer = new Label( "This obviously needs style, Drywit." );

		final ListBox roomSelector = new ListBox();
		final TextBox handleField = new TextBox();

		// TODO Make this more modular (room names & ids)
		roomSelector.addItem( "Main Room", "1" );

		Button joinButton = new Button( "Join the room", new ClickHandler() {
			public void onClick( ClickEvent click ) {
				// Set the session's active room
				activeRoom = new Integer( roomSelector.getValue( roomSelector.getSelectedIndex() ) );

				// Grab the user's requested handle
				String handle = handleField.getText();

				JoinServiceAsync joinService = GWT.create( JoinService.class );

				// Register this user
				joinService.join( activeRoom, handle, new JoinHandler() );
			}
		} );

		joinPanel.add( disclaimer );
		joinPanel.add( roomSelector );
		joinPanel.add( handleField );
		joinPanel.add( joinButton );

		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add( joinPanel );

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

			public void onSuccess( final DisplayMessage result ) {

				// Score the new message
				// TODO Clean this up!
				MessageScoreServiceAsync scoringSvc = GWT.create( MessageScoreService.class );
				scoringSvc.scoreMessage( activeRoom, result.getMessageId(), new AsyncCallback< MessageScore >() {
					public void onFailure( Throwable caught ) {
						Window.alert( "Something went wrong in the scoring service" );
					}

					public void onSuccess( MessageScore score ) {
						result.setScore( score.getScore() );
						result.setScoreReason( score.getExplanation() );

						room.handleIncomingMessage( result );
					}
				} );

				// Add the message to the room
				room.addMessage( result );
			}
		};

		// Call the MessagePostedService
		messagePostedSvc.postMessage( activeRoom, event.getMessage(), callback );
	}

	@Override
	public void onNewMessagesAvailable( NewMessagesAvailableEvent event ) {
		for ( DisplayMessage message : event.getNewMessages() ) {
			room.handleIncomingMessage( message );
		}
	}

	/**
	 * Private inner class which implements the callback for logging in. On
	 * successful login, this will prompt the user for a handle.
	 */
	private class LoginHandler implements AsyncCallback< LoginInfo > {

		public void onFailure( Throwable error ) {
			// TODO Handle an error on login more elegantly
			Window.alert( error.getMessage() );
		}

		public void onSuccess( LoginInfo result ) {
			loginInfo = result;

			if ( loginInfo.isLoggedIn() ) {
				showJoinScreen();
			} else {
				showLoginScreen();
			}
		}
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

			// Set this session's user
			user = dUser;

			// Add the appropriate Mockery CSS
			css.ensureInjected();

			// Create UI elements
			scoreboard = new Scoreboard();
			room = new Room();
			inputField = new InputField();
			poller = new UpdatePoller( activeRoom );

			// Add the user to the scoreboard
			scoreboard.addUser( user.getHandle(), user.getCumulativeScore() );

			// Set up user interface
			initializeUI();

			// Set up listeners for events
			initializeHandlers();

			// Start polling for new messages
			poller.startPolling();

		}
	}

}
