package com.zw.rpn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zw.rpn.exception.RPNException;
import com.zw.rpn.parser.DefaultRPNParser;

/**
 * This class provides the unit test for the {
 * 
 * @link DefaultRPNParser } class.
 */
@SuppressWarnings("nls") // No need for i18n checks in this unit test.
public class DefaultRPNParserTest extends BaseTest {

	// Constants used for composers array below
	private static final String BACH = "Bach";
	private static final String BEETHOVEN = "Beethoven";
	private static final String CHOPIN = "Chopin";
	private static final String HANDEL = "Handel";
	private static final String LIZST = "Lizst";
	private static final String MOZART = "Mozart";

	// This is used for ensuring items are added to the model in the correct
	// order
	private String[] composers = {

			BACH, BEETHOVEN, CHOPIN, HANDEL, LIZST, MOZART

	};

	// The parser we'll be using for our tests.
	private DefaultRPNParser parser;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		parser = new DefaultRPNParser();
		assertNotNull(parser);

	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

		parser = null;

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNParser#parse(String) } using a <code>null</code>
	 *       <code>line</code> parameter.
	 */
	@Test
	public void testParseNull() {

		String line = null;
		List<String> tokens = null;

		// Test passing null as value for "line" parameter; should return
		// non-null, but empty model
		tokens = parser.parse(line);
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNParser#parse(String) } using an empty <code>line</code>
	 *       parameter.
	 */
	@Test
	public void testParseEmpty() {

		String line = null;
		List<String> tokens = null;

		// Test passing empty string; should return non-null, but empty model
		line = "";
		tokens = parser.parse(line);
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNParser#parse(String) } using an empty <code>line</code>
	 *       parameter.
	 */
	@Test
	public void testParseSingleToken() throws RPNException {

		String line = null;
		List<String> tokens = null;
		String token = "token";
		String result = null;

		// Test passing string containing one token; should return non-null,
		// non-empty model.
		line = token;
		tokens = parser.parse(line);
		assertNotNull(tokens);
		assertFalse(tokens.isEmpty());

		// Let's ensure the item in the model matches the token we passed in.
		result = tokens.remove(0);
		assertNotNull(result);
		assertTrue(result.equals(token));

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNParser#parse(String) } using an empty <code>line</code>
	 *       parameter.
	 */
	@Test
	public void testParseMultipleTokens() throws RPNException {

		String line = null;
		List<String> tokens = null;
		String result = null;
		int numItems = composers.length;
		StringBuilder sb = new StringBuilder();

		// Stuff the items in composers array into the line that will be parsed
		for (int i = 0; i < numItems; i++) {

			sb.append(composers[i]).append(" ");

		}
		line = sb.toString().trim();

		// Parse the line, ensuring the model returned isn't null, nor empty and
		// that it contains the right # of items.
		tokens = parser.parse(line);
		assertNotNull(tokens);
		assertFalse(tokens.isEmpty());
		assertTrue(tokens.size() == numItems);

		// Now let's make sure the items were added to the model in the correct
		// order.
		for (int i = numItems - 1; i >= 0; i--) {

			result = tokens.remove(i);
			assertNotNull(result);
			assertTrue(result.equals(composers[i]));

		}

	}

}