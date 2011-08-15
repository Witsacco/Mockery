package com.witsacco.mockery.server;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellResponse;


public class MessageJudge {

	// Single instance of MessageJudge
	private static final MessageJudge instance = new MessageJudge();

	// Single instance of SpellChecker
	private static SpellChecker checker = new SpellChecker();
	
	// Empty constructor
	private MessageJudge() {
	}

	// Get single instance of MessageJudge
	public static MessageJudge getInstance() {
		return instance;
	}
	
	public MessageScore evaluateMessage( Message message ) {

		// Get message body
		String body = message.getBody();
		
		int score = 0;
		ArrayList< String > reasons = new ArrayList< String >();
		
		// Subtract points for incorrect spelling
		if ( hasSpellingErrors( body ) ) {
			score -= 10;
			reasons.add( "Bad spelling" );
		}

		return new MessageScore( score, StringUtils.join(reasons, ", ") );
	}
	
	/**
	 * Check message for the existance of spelling errors
	 * 
	 * @param messageBody Message to be checked for spelling errors
	 * @return true if the message had spelling errors, false otherwise
	 */
	private boolean hasSpellingErrors( String messageBody ) {
		
		SpellResponse spellResponse = checker.check( messageBody );

		return ( spellResponse.getCorrections().length > 0 ? true : false);
	}
}
