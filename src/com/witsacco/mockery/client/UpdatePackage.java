package com.witsacco.mockery.client;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Represents an update to a room, with its most current standings and list of messages.
 */
public class UpdatePackage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList< DisplayUser > currentStandings;
	private ArrayList< DisplayMessage > messages;

	public UpdatePackage() {
		// Need this for use on the server side.
	}

	/**
	 * @param currentStandings
	 * @param messages
	 */
	public UpdatePackage( ArrayList< DisplayUser > currentStandings, ArrayList< DisplayMessage > messages ) {
		super();
		this.currentStandings = currentStandings;
		this.messages = messages;
	}

	/**
	 * @return the currentStandings
	 */
	public ArrayList< DisplayUser > getCurrentStandings() {
		return currentStandings;
	}

	/**
	 * @param currentStandings
	 *            the currentStandings to set
	 */
	public void setCurrentStandings( ArrayList< DisplayUser > currentStandings ) {
		this.currentStandings = currentStandings;
	}

	/**
	 * @return the messages
	 */
	public ArrayList< DisplayMessage > getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages( ArrayList< DisplayMessage > messages ) {
		this.messages = messages;
	}

}
