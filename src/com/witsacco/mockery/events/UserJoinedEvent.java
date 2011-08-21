package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.DisplayUser;

public class UserJoinedEvent extends GwtEvent< UserJoinedEventHandler > {

	public static Type< UserJoinedEventHandler > TYPE = new Type< UserJoinedEventHandler >();

	private DisplayUser user;
	private int roomId;
	
	public UserJoinedEvent(DisplayUser dUser, int room ) {
		user = dUser;
		roomId = room;
	}

	@Override
	public Type< UserJoinedEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( UserJoinedEventHandler handler ) {
		handler.onUserJoined( this );
	}
	
	public DisplayUser getUser() {
		return user;
	}
	
	public int getRoomId() {
		return roomId;
	}
}
