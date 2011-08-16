package com.witsacco.mockery.client;

import java.io.Serializable;

/**
 * This is the client-side class representing a message for display in the UI.
 * It has an id to enable the app to get a specific instance and update it, a
 * roomId to assign the message to a room, a body with the content of the
 * message, the name of its author (authorName) and a score.
 */
public class DisplayMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	// The id of the persisted message
	private long messageId;

	// The id of the room that contains this message
	private int roomId;

	// The content of the message
	private String body;

	// The author's name
	private String authorName;

	// This message's score (note: this is an Integer so we can use null to mean
	// "not scored"
	private Integer score;

	// This message's score reason
	private String scoreReason;

	public DisplayMessage() {
		// This no-argument constructor is necessary so this class can be used
		// in the MessagePostedService.
	}

	/**
	 * @param messageId
	 * @param roomId
	 * @param body
	 * @param authorName
	 */
	public DisplayMessage( long messageId, int roomId, String body, String authorName ) {
		this.messageId = messageId;
		this.roomId = roomId;
		this.body = body;
		this.authorName = authorName;
	}

	/**
	 * @param messageId
	 * @param roomId
	 * @param body
	 * @param authorName
	 * @param score
	 * @param scoreReason
	 */
	public DisplayMessage( long messageId, int roomId, String body, String authorName, Integer score, String scoreReason ) {
		this.messageId = messageId;
		this.roomId = roomId;
		this.body = body;
		this.authorName = authorName;
		this.score = score;
		this.scoreReason = scoreReason;
	}

	/**
	 * @return the messageId
	 */
	public long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId( long messageId ) {
		this.messageId = messageId;
	}

	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId
	 *            the roomId to set
	 */
	public void setRoomId( int roomId ) {
		this.roomId = roomId;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody( String body ) {
		this.body = body;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName( String authorName ) {
		this.authorName = authorName;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		if ( null == score )
			return null;

		return score.toString();
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore( Integer score ) {
		this.score = score;
	}

	/**
	 * @return the scoreReason
	 */
	public String getScoreReason() {
		return scoreReason;
	}

	/**
	 * @param scoreReason
	 *            the scoreReason to set
	 */
	public void setScoreReason( String scoreReason ) {
		this.scoreReason = scoreReason;
	}

	/*
	 * Defines equality for DisplayMessages to be if they have the same ID.
	 */
	public boolean equals( Object obj ) {

		if ( obj instanceof DisplayMessage ) {
			return ( this.messageId == ( ( DisplayMessage ) obj ).getMessageId() );
		}

		return super.equals( obj );

	}

}
