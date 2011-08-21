package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface MessagePostedEventHandler extends EventHandler {
	void onMessagePosted( MessagePostedEvent event );
}
