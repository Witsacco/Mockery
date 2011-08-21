package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface UserJoinedEventHandler extends EventHandler {
	void onUserJoined( UserJoinedEvent event );
}
