package com.witsacco.mockery.events;

import com.google.gwt.event.shared.EventHandler;

public interface ScoreboardUpdateAvailableEventHandler extends EventHandler {

	void onScoreboardUpdateAvailable( ScoreboardUpdateAvailableEvent event );

}