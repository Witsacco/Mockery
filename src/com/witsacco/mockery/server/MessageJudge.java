package com.witsacco.mockery.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

import com.witsacco.mockery.client.MessageScore;

public class MessageJudge {

	// Single instance of MessageJudge
	private static final MessageJudge instance = new MessageJudge();

	// Single instance of SpellChecker
	private static SpellChecker checker = new SpellChecker();

	private static final int RANDOM_LIMIT = 3;

	private static Random rand = new Random();
	
	private HashMap<String, Integer> wordValues;

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
		score += scorePhrase( messageBody );
		
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
	
	private int scorePhrase( String phrase ) {
		int sumScore = 0;
		
		StringTokenizer st = new StringTokenizer( phrase);

		while (st.hasMoreTokens()) {
			sumScore += scoreWord( st.nextToken() );
		}
		
		//TODO: remove words that were present in the scoring map from spelling consideration
		
		return sumScore;
	}
	
	private int scoreWord( String word ) {
		
		Integer score = wordValues.get( word.toLowerCase() ); 
		
		if ( score != null ) {
			return score; 
		}
		
		return 0;
	}
	
	private void initWordValues() {
		wordValues = new HashMap< String, Integer >();
		
		// Keys should be lowercase.  When scoring, incoming words will be converted to lowercase
		wordValues.put( "nyu", 5 );
		wordValues.put( "nell", -10 );
		wordValues.put( "mets", -5 );
		wordValues.put( "molitor", 3 );
		wordValues.put( "lol", -5 );
		wordValues.put( ":)", -15 );
	}
}
