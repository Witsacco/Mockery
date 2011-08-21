package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface InitialLoadEventHandler extends EventHandler {
	void onInitialLoad( InitialLoadEvent event );
}
