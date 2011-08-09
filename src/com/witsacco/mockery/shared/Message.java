package com.witsacco.mockery.shared;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	String body;
	MockeryUser author;
	Date createTime;
	// MessageScore score;
	int roomId;
	int id;

	// public Message( String body, int roomId, MockeryUser user ) {
	// this.createTime = new Date();
	//
	// this.body = body;
	// this.roomId = roomId;
	// this.author = user;
	//
	// //TODO generate message ID
	// }

	public Message() {

	}

	public void populate( String body, int roomId, MockeryUser user ) {
		this.createTime = new Date();

		this.body = body;
		this.roomId = roomId;
		this.author = user;
	}

	public String getBody() {
		return body;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getRoomId() {
		return roomId;
	}

	public MockeryUser getAuthor() {
		return author;
	}

	// TODO Generate ID getter
}
