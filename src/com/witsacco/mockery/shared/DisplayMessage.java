package com.witsacco.mockery.shared;

import java.io.Serializable;

public class DisplayMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nickname;
	private String body;

	public DisplayMessage() {}
	
	public DisplayMessage( String nick, String bd ) {
		nickname = nick;
		body = bd;
	}
	
	// Copy a full Message into a lightweight DisplayMessage
	public DisplayMessage( Message fullMessage ) {
		nickname = fullMessage.getAuthor().getNickname();
		body = fullMessage.getBody();
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname( String nickname ) {
		this.nickname = nickname;
	}

	public String getBody() {
		return body;
	}

	public void setBody( String body ) {
		this.body = body;
	}

}
