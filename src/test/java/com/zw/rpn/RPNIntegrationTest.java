package com.zw.rpn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zw.rpn.evaluator.DefaultRPNEvaluator;
import com.zw.rpn.evaluator.RPNEvaluator;
import com.zw.rpn.exception.RPNException;
import com.zw.rpn.exception.RPNException.ErrorCode;
import com.zw.rpn.parser.DefaultRPNParser;
import com.zw.rpn.parser.RPNParser;

/**
 * This class provides an integration unit test, whereby the process of parsing
 * and evaluating an input {
 * @link String } is put to the test.
 */
@SuppressWarnings("nls") // i18n not supported in unit test
public class RPNIntegrationTest extends BaseTest {

	private RPNParser<String> parser;
	private RPNEvaluator<String> evaluator;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new DefaultRPNParser();
		evaluator = new DefaultRPNEvaluator();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		evaluator = null;
		parser = null;
	}

	// Just a convenience for a one-stop call to evaluate an expression.
	private double evaluate(String line) throws RPNException {
		RPNModel<String> model = new RPNStackModel();
		return evaluator.evaluate(parser.parse(line), model);
	}

	private ErrorCode performTest(String line, boolean doesThrowException, double value) {

		ErrorCode errorCode = ErrorCode.UNKNOWN;
		boolean exceptionCaught = false;
		double result = Double.NaN;

		try {
			result = evaluate(line);
		} catch (RPNException e) {
			exceptionCaught = true;
			errorCode = e.getErrorCode();
		}

		if (doesThrowException) {
			assertTrue(exceptionCaught);
		} else {
			assertFalse(exceptionCaught);
		}

		if (Double.isNaN(value)) {
			assertTrue(Double.isNaN(result));
		} else {
			assertTrue(result == value);
		}

		return errorCode;

	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the input to be parsed is <code>null</code>.
	 */
	@Test
	public void testForFailNullLine() {

		String line = null;
		double result = Double.NaN;

		// Test passing null line into the parser; should not throw exception
		// but result will be NaN
		ErrorCode errorCode = performTest(line, false, result);
		assertTrue(errorCode.isUNKNOWN());

	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input is empty.
	 */
	@Test
	public void testForFailEmptyLine() {

		String line = "";
		double result = Double.NaN;

		// Test passing empty line into the parser; should not throw exception
		// but result will be NaN
		ErrorCode errorCode = performTest(line, false, result);
		assertTrue(errorCode.isUNKNOWN());

	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input does not consist of any numbers or
	 * operators.
	 */
	@Test
	public void testForFailNoDigitsNoOperators() {

		String line = "One Two Three";
		double result = Double.NaN;

		// Test passing line with no digits or operators into the parser; should
		// throw exception and result will be NaN
		ErrorCode errorCode = performTest(line, true, result);
		assertTrue(errorCode.isEvaluationError());

	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input does not consist of any numbers but does
	 * have operators embedded.
	 */
	@Test
	public void testForFailNoDigitsWithOperators() {

		String line = "One Two Three +";
		double result = Double.NaN;

		// Test passing line with no digits but operators into the parser;
		// should throw exception and result will be NaN
		ErrorCode errorCode = performTest(line, true, result);
		assertTrue(errorCode.isEvaluationError());
	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input contains numbers but does not have any
	 * operators.
	 */
//	@Test
//	public void testForFailDigitsNoOperators() {
//		String line = "1 2 3 ";
//		double result = Double.NaN;
//
//		// Test passing line with no digits but operators into the parser;
//		// should throw exception and result will be NaN
//		ErrorCode errorCode = performTest(line, true, result);
//		assertTrue(errorCode.isTooManyArguments());
//	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input contains numbers but not enough operators.
	 */
//	@Test
//	public void testForFailDigitsTooFewOperators() {
//
//		String line = "1 2 3 +";
//		double result = Double.NaN;
//
//		// Test passing line with no digits but operators into the parser;
//		// should throw exception and result will be NaN
//		ErrorCode errorCode = performTest(line, false, result);
//		assertTrue(errorCode.isTooManyArguments());
//	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input contains numbers but not enough operators.
	 */
//	@Test
//	public void testForFailDigitsTooManyOperators() {
//
//		String line = "1 2 + - ";
//		double result = Double.NaN;
//
//		// Test passing line with no digits but operators into the parser;
//		// should throw exception and result will be NaN
//		ErrorCode errorCode = performTest(line, false, result);
//		assertTrue(errorCode.isTooFewArguments());
//	}

	/**
	 * Test method for ensuring the parsing and evaluation process fails as
	 * expected when the parsed input doesn't contain the right amount of
	 * numbers for the number of operators.
	 */
//	@Test
//	public void testForFailDigitsTooManyArguments() {
//
//		String line = "1 2 3 +";
//		double result = Double.NaN;
//
//		// Test passing line with no digits but operators into the parser;
//		// should throw exception and result will be NaN
//		ErrorCode errorCode = performTest(line, false, result);
//		assertTrue(errorCode.isTooManyArguments());
//	}

	/**
	 * Test method for ensuring the parsing and evaluation process succeeds as
	 * expected.
	 */
	@Test
	public void testForSuccess() {

		String line = "1 2 +";
		double result = 1 + 2;// = 3;

		// Test passing line with valid numbers and operators, simple case.
		ErrorCode errorCode = performTest(line, false, result);
		assertTrue(errorCode.isUNKNOWN());

		// A little more complex of an expression
		line = "1 2 3 + *";
		result = 1 * (2 + 3); // = 5
		errorCode = performTest(line, false, result);
		assertTrue(errorCode.isUNKNOWN());

		// Even more complex expression
		line = "2 3 + 4 5 + *";
		result = (2 + 3) * (4 + 5); // = 45;
		errorCode = performTest(line, false, result);
		assertTrue(errorCode.isUNKNOWN());

	}

}