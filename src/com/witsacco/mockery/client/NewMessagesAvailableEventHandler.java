package com.witsacco.mockery.client;

import com.google.gwt.event.shared.EventHandler;

public interface NewMessagesAvailableEventHandler extends EventHandler {
	void onNewMessagesAvailable( NewMessagesAvailableEvent event );
}
