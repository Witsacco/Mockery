package com.witsacco.mockery.server;

public class MessageScore {
	
	int score;
	String explanation;
	
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
