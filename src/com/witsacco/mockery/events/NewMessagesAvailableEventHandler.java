package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface NewMessagesAvailableEventHandler extends EventHandler {
	void onNewMessagesAvailable( NewMessagesAvailableEvent event );
}
