package com.witsacco.mockery.client;

import java.io.Serializable;

public class MessageScore implements Serializable {

	private static final long serialVersionUID = 1L;

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
