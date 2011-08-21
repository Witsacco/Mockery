package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface MessageEnteredEventHandler extends EventHandler {
	void onMessageEntered( MessageEnteredEvent event );
}
