package com.witsacco.mockery.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.DisplayUser;

public class ScoreboardUpdateAvailableEvent extends GwtEvent< ScoreboardUpdateAvailableEventHandler > {

	public static Type< ScoreboardUpdateAvailableEventHandler > TYPE = new Type< ScoreboardUpdateAvailableEventHandler >();

	private ArrayList< DisplayUser > currentStandings;

	public ScoreboardUpdateAvailableEvent( ArrayList< DisplayUser > currentStandings ) {
		this.currentStandings = currentStandings;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type< ScoreboardUpdateAvailableEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( ScoreboardUpdateAvailableEventHandler handler ) {
		handler.onScoreboardUpdateAvailable( this );
	}

	/**
	 * @return the currentStandings
	 */
	public ArrayList< DisplayUser > getCurrentStandings() {
		return currentStandings;
	}
}
