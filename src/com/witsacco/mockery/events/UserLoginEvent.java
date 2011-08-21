package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.LoginInfo;

public class UserLoginEvent extends GwtEvent< UserLoginEventHandler > {

	public static Type< UserLoginEventHandler > TYPE = new Type< UserLoginEventHandler >();

	private LoginInfo loginInfo;
	
	public UserLoginEvent( LoginInfo login ) {
		loginInfo = login;
	}

	@Override
	public Type< UserLoginEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( UserLoginEventHandler handler ) {
		handler.onUserLogin( this );
	}
	
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
}
