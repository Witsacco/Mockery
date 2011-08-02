package com.witsacco.mockery.server;

import java.util.Date;

public class Message {

	String body;
//	User author;
	Date createTime;
//	MessageScore score;
	int roomId;
	int id;
	
	public Message( String body, int roomId ) {
		super();
		this.body = body;
		this.createTime = new Date();
		this.roomId = roomId;
		
		//TODO generate message ID
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
	
	//TODO Generate ID getter
}
