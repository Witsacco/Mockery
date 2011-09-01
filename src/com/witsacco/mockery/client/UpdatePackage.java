package com.witsacco.mockery.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * Represents an update to a room, with its most current standings and list of messages.
 */
public class UpdatePackage implements Serializable {

	private static final long serialVersionUID = 1L;

	// The time this UpdatePackage was created
	private Date timestamp;

	// Current standings as of `timestamp`
	private ArrayList< DisplayUser > currentStandings;

	// Messages that are new or have been updated as of `timestamp`
	private ArrayList< DisplayMessage > updatedMessages;

	public UpdatePackage() {
		// Need this for use on the server side.
	}

	/**
	 * @param currentStandings
	 * @param messages
	 */
	public UpdatePackage( ArrayList< DisplayUser > currentStandings, ArrayList< DisplayMessage > messages ) {
		timestamp = new Date();

		this.currentStandings = currentStandings;
		this.updatedMessages = messages;
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
	public ArrayList< DisplayMessage > getUpdatedMessages() {
		return updatedMessages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setUpdatedMessages( ArrayList< DisplayMessage > messages ) {
		this.updatedMessages = messages;
	}

	/**
	 * @return the Date when this UpdatePackage was created
	 */
	public Date getTimestamp() {
		return timestamp;
	}

}
