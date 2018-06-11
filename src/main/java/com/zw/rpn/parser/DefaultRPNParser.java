package com.zw.rpn.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * This class provides a default implementation of an {
 * 
 * @link RPNParser }. This one uses a {
 * @link StringTokenizer }, which isn't the most efficient means for parsing
 *       tokens from the input line, but does provide a somewhat easier
 *       implementation.
 * 
 */
public class DefaultRPNParser implements RPNParser<String> {

	/**
	 * Add this to <code>log4j.properties</code> for enabling output to console.
	 * log4j.logger.com.kermel.rpncalc.parser.DefaultRPNParser=DEBUG
	 */
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(DefaultRPNParser.class);

	private StringTokenizer tokenizer;

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	@SuppressWarnings("nls") // No need for i18n checks in here
	public List<String> parse(String line) {

		List<String> tokens = new ArrayList<String>();

		if (line != null) {
			tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				tokens.add(token);				
			}
			
//			if (LOG.isDebugEnabled()) {
//				LOG.debug(String.format("   stack : \"%s\" ", tokens));
//			}
		}
		return tokens;
	}

}