package com.witsacco.mockery.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

import com.google.gwt.dev.util.Pair;
import com.witsacco.mockery.client.MessageScore;

public class MessageJudge {

	// Single instance of MessageJudge
	private static final MessageJudge instance = new MessageJudge();

	// Single instance of SpellChecker
	private static SpellChecker checker = new SpellChecker();

	private static final int RANDOM_LIMIT = 3;

	private static Random rand = new Random();

	private HashMap< String, WordScore > wordValues;

	// Empty constructor
	private MessageJudge() {

		initWordValues();

	}

	// Get single instance of MessageJudge
	public static MessageJudge getInstance() {
		return instance;
	}

	public MessageScore evaluateMessage( String messageBody ) {

		int score = getRandomFluctuation();

		ArrayList< String > reasons = new ArrayList< String >();

		// Subtract points for incorrect spelling
		if ( hasSpellingErrors( messageBody ) ) {
			score -= 10;
			reasons.add( "Bad spelling" );
		}

		// Compare to our set of words with pre-defined scores
		for (WordScore wordScore : scorePhrase( messageBody ) ) {
			score += wordScore.getScore();
			reasons.add( wordScore.getReason() );
		}

		return new MessageScore( score, reasons );
	}

	/**
	 * Check message for the existence of spelling errors
	 * 
	 * @param messageBody
	 *            Message to be checked for spelling errors
	 * @return true if the message had spelling errors, false otherwise
	 */
	private boolean hasSpellingErrors( String messageBody ) {

		SpellResponse spellResponse = checker.check( messageBody );

		SpellCorrection[] corrections = spellResponse.getCorrections();

		if ( null == corrections ) {
			return false;
		}

		else if ( corrections.length == 0 ) {
			return false;
		}

		return true;
	}

	private int getRandomFluctuation() {
		return rand.nextInt( RANDOM_LIMIT * 2 ) - RANDOM_LIMIT + 1;
	}

	private ArrayList< WordScore > scorePhrase( String phrase ) {
		ArrayList< WordScore > scores = new ArrayList< WordScore >();

		StringTokenizer st = new StringTokenizer( phrase );

		while ( st.hasMoreTokens() ) {
			WordScore score = scoreWord( st.nextToken() );

			if ( score != null ) {
				scores.add( score );
			}
		}

		// TODO: remove words that were present in the scoring map from spelling
		// consideration

		return scores;
	}

	private WordScore scoreWord( String word ) {

		WordScore score = wordValues.get( word.toLowerCase() );

		if ( score != null ) {
			return score;
		}

		return null;
	}

	private void initWordValues() {
		wordValues = new HashMap< String, WordScore >();

		// Keys should be lowercase. When scoring, incoming words will be
		// converted to lowercase
		wordValues.put( "nyu", new WordScore( 5, "Violet Pride!!!" ) );
		wordValues.put( "nell", new WordScore( -10, "Don't mention the ex" ) );
		wordValues.put( "mets", new WordScore( -5, "Only 15 games back..." ) );
		wordValues.put( "molitor", new WordScore( 3, "Don't mess with Molly" ) );
		wordValues.put( "lol", new WordScore( -5, "Lawl" ) );
		wordValues.put( ":)", new WordScore( -15, "More like :(" ) );
	}

	private class WordScore {

		private int score;
		private String reason;

		public WordScore( int score, String reason ) {
			this.score = score;
			this.reason = reason;
		}

		public int getScore() {
			return score;
		}

		public String getReason() {
			return reason;
		}
	}
}
