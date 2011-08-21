package com.witsacco.mockery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.witsacco.mockery.events.UserLoginEvent;
import com.witsacco.mockery.events.UserLoginEventHandler;
import com.witsacco.mockery.resources.LoginCSS;
import com.witsacco.mockery.resources.MockeryResources;
import com.witsacco.mockery.services.LoginService;
import com.witsacco.mockery.services.LoginServiceAsync;

public class LoginScreen extends FlowPanel implements AsyncCallback< LoginInfo >, HasHandlers {

	private LoginCSS css = MockeryResources.INSTANCE.loginCss();

	private LoginInfo loginInfo;

	// GWT event handler manager
	private HandlerManager handlerManager;

	LoginServiceAsync loginService;

	public LoginScreen() {
		// Call superclass constructor
		super();

		handlerManager = new HandlerManager( this );

		// Set up a new login service
		loginService = GWT.create( LoginService.class );
	}

	public void login() {
		// Attempt to log the user in
		loginService.login( GWT.getHostPageBaseURL(), this );
	}

	private void initializeUI() {
		css.ensureInjected();

		addStyleName( css.loginPanel() );

		// Labels for the login screen
		add( new Label( "Welcome to Mockery, a-hole." ) );
		add( new Label( "To get started, sign in with your Google account." ) );

		// Login button and panel
		Anchor loginButton = new Anchor( "Sign in with Google" );
		loginButton.addStyleName( "login-button" );
		loginButton.setHref( loginInfo.getLoginUrl() );

		FlowPanel loginButtonPanel = new FlowPanel();
		loginButtonPanel.add( loginButton );
		add( loginButtonPanel );

		add( new Label( "Be prepared to bring your A game." ) );
	}

	public void onFailure( Throwable error ) {
		// TODO Handle an error on login more elegantly
		Window.alert( error.getMessage() );
	}

	public void onSuccess( LoginInfo result ) {
		loginInfo = result;

		// Initialize the UI for the login panel
		initializeUI();

		// Fire an event informing listeners that a login occurred
		fireEvent( new UserLoginEvent( result ) );
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	public HandlerRegistration addUserLoginEventHandler( UserLoginEventHandler handler ) {
		return handlerManager.addHandler( UserLoginEvent.TYPE, handler );
	}

}
