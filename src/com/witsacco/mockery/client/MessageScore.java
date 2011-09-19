package com.witsacco.mockery.client;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageScore implements Serializable {

	private static final long serialVersionUID = 1L;

	// The message this score is for
	private long messageId;

	private int score;
	private String explanation;

	public MessageScore() {
		// This no-argument constructor is necessary so this class can be used
		// in the MessageScoreService.
	}

	/**
	 * @param score
	 * @param explanation
	 */
	public MessageScore( int score, String explanation ) {
		this.score = score;
		this.explanation = explanation;
	}

	public MessageScore( int score, ArrayList< String > reasons ) {
		this.score = score;

		if ( reasons.size() > 0 ) {
			StringBuilder sb = new StringBuilder();

			for ( String reason : reasons ) {
				sb.append( reason + ", " );
			}

			String expl = sb.toString();
			expl = expl.substring( 0, expl.length() - 2 );

			this.explanation = expl;
		}
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
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the explanation
	 */
	public String getExplanation() {
		return explanation;
	}
}
