package com.witsacco.mockery.client;

import java.io.Serializable;

/**
 * This is the client-side class representing a user for display in the UI. It
 * has a handle (nickname) for display and a cumulative score derived from all
 * of the user's messages.
 */
public class DisplayUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// The handle of the user
	private String handle;

	// The user's cumulative score
	private Long cumulativeScore;

	// Whether or not this user is logged in
	private boolean loggedIn;

	public DisplayUser() {
		// This no-argument constructor is necessary so this class can be used
		// in the JoinService.
	}

	/**
	 * @param cumulativeScore
	 * @param loggedIn
	 */
	public DisplayUser( String handle, Long cumulativeScore, boolean loggedIn ) {
		this.handle = handle;
		this.cumulativeScore = cumulativeScore;
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the handle
	 */
	public String getHandle() {
		return handle;
	}

	/**
	 * @param handle
	 *            the handle to set
	 */
	public void setHandle( String handle ) {
		this.handle = handle;
	}

	/**
	 * @return the cumulativeScore
	 */
	public Long getCumulativeScore() {
		return cumulativeScore;
	}

	/**
	 * @param cumulativeScore
	 *            the cumulativeScore to set
	 */
	public void setCumulativeScore( Long cumulativeScore ) {
		this.cumulativeScore = cumulativeScore;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn( boolean loggedIn ) {
		this.loggedIn = loggedIn;
	}

	public boolean equals( Object aThat ) {
		// Check if the two object references are actuall referring to the same memory location
		if ( this == aThat ) {
			return true;
		}

		// If we're not comparing to a DisplayUser, then we know they're not equal
		if ( !( aThat instanceof DisplayUser ) ) {
			return false;
		}

		// Cast to a DisplayUser
		DisplayUser that = ( DisplayUser ) aThat;

		// Compare on each of the fields
		return ( this.handle.equals( that.handle ) && this.cumulativeScore == that.cumulativeScore && this.loggedIn == that.loggedIn );
	}
}