package com.witsacco.mockery.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@Entity
public class Message {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Key key;

	// The user who entered this message
	// TODO Change this from a GWT user into something else
	private User author;

	// The content of this message
	private String body;

	// The auto-generated time of this message
	private Date createTime;

	// The room this message was created in
	private int roomId;

	// The score assigned to this message by the MessageJudge
	private MessageScore score;

	/**
	 * @param key
	 * @param author
	 * @param body
	 * @param createTime
	 * @param roomId
	 * @param score
	 */
	public Message( int roomId, String body, User author ) {
		this.roomId = roomId;
		this.body = body;
		this.author = author;

		// Add the current timestamp
		this.createTime = new Date();
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor( User author ) {
		this.author = author;
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
	 * @return the score
	 */
	public MessageScore getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore( MessageScore score ) {
		this.score = score;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

}
