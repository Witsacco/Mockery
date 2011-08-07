package com.witsacco.mockery.client;

import com.google.gwt.event.shared.EventHandler;

public interface MessagePostedEventHandler extends EventHandler {
	void onMessageReceived( MessagePostedEvent event );
}
