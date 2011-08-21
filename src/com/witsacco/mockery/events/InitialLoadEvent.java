package com.witsacco.mockery.events;

import com.google.gwt.event.shared.GwtEvent;
import com.witsacco.mockery.client.UpdatePackage;

public class InitialLoadEvent extends GwtEvent< InitialLoadEventHandler > {

	public static Type< InitialLoadEventHandler > TYPE = new Type< InitialLoadEventHandler >();

	private UpdatePackage initialState;
	
	public InitialLoadEvent(UpdatePackage initState ) {
		initialState = initState;
	}

	@Override
	public Type< InitialLoadEventHandler > getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch( InitialLoadEventHandler handler ) {
		handler.onInitialLoad( this );
	}
	
	public UpdatePackage getInitialState() {
		return initialState;
	}
}
