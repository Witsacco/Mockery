package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface MessageScoredEventHandler extends EventHandler {
	void onMessageScored( MessageScoredEvent event );
}
