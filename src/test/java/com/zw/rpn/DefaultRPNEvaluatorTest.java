package com.zw.rpn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zw.rpn.evaluator.DefaultRPNEvaluator;
import com.zw.rpn.exception.RPNException;
import com.zw.rpn.exception.RPNException.ErrorCode;
import com.zw.rpn.operation.BasicOperation;

/**
 * This class provides the unit test for the {
 * 
 * @link DefaultRPNEvaluator } class.
 */
@SuppressWarnings("nls") // i18n checks not necessary in unit test
public class DefaultRPNEvaluatorTest extends BaseTest {

	private DefaultRPNEvaluator evaluator;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		evaluator = new DefaultRPNEvaluator();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		evaluator = null;
	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#isValue(String) }.
	 */
	@Test
	public void testIsValue() {
		String token = null;
		boolean result = false;

		// Test with token being null
		result = evaluator.isValue(token);
		assertFalse(result);

		// Test with empty token
		token = "";
		result = evaluator.isValue(token);
		assertFalse(result);

		// Test with token that doesn't represent a value
		token = "X";
		result = evaluator.isValue(token);
		assertFalse(result);

		// Test with token that almost represents a value, but not quite
		token = "100s";
		result = evaluator.isValue(token);
		assertFalse(result);

		// Test with token that is a value
		token = "200";
		result = evaluator.isValue(token);
		assertTrue(result);
	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#determineOperation(String) }.
	 */
	@Test
	public void testDetermineOperation() {

		String token = null;
		BasicOperation operation = null;

		// Test using null token
		operation = (BasicOperation) evaluator.determineOperation(token);
		assertTrue(operation.isUNKNOWN());

		// Test using empty token
		token = "";
		operation = (BasicOperation) evaluator.determineOperation(token);
		assertTrue(operation.isUNKNOWN());

		// Test using unknown operation token
		token = "x";
		operation = (BasicOperation) evaluator.determineOperation(token);
		assertTrue(operation.isUNKNOWN());

		// Test using all known operations
		BasicOperation[] allOperations = BasicOperation.values();
		for (BasicOperation op : allOperations) {

			token = op.toString();
			operation = (BasicOperation) evaluator.determineOperation(token);
			assertTrue(operation.equals(op));

		}

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#convertToValue(String) }.
	 */
	@Test
	public void testConvertToValue() throws RPNException {

		String token = null;
		double value = Double.NaN;
		boolean exceptionCaught = false;
		double targetValue = 12345;
		ErrorCode errorCode = ErrorCode.UNKNOWN;

		// Test passing null token
		value = evaluator.convertToValue(token);
		assertTrue(Double.isNaN(value));

		// Test passing empty token
		token = "";
		value = evaluator.convertToValue(token);
		assertTrue(Double.isNaN(value));

		// Test passing non-value token; should throw an RPNException
		token = "blue";
		try {
			value = evaluator.convertToValue(token);
		} catch (RPNException e) {
			exceptionCaught = true;
			errorCode = e.getErrorCode();
		}
		assertTrue(Double.isNaN(value));
		assertTrue(exceptionCaught);
		assertTrue(errorCode.isNotUNKNOWN());
		assertTrue(errorCode.isParsingError());

		// Test passing token that's almost a value (has extra characters in
		// it).
		exceptionCaught = false;
		errorCode = ErrorCode.UNKNOWN;
		token = "123x";
		try {
			value = evaluator.convertToValue(token);
		} catch (RPNException e) {
			exceptionCaught = true;
			errorCode = e.getErrorCode();
		}
		assertTrue(Double.isNaN(value));
		assertTrue(exceptionCaught);
		assertTrue(errorCode.isNotUNKNOWN());
		assertTrue(errorCode.isParsingError());

		// Test with token that represents a value
		token = Double.toString(targetValue);
		errorCode = ErrorCode.UNKNOWN;
		exceptionCaught = false;
		try {
			value = evaluator.convertToValue(token);
		} catch (RPNException e) {
			exceptionCaught = true;
		}
		assertFalse(exceptionCaught);
		assertFalse(Double.isNaN(value));
		assertTrue(value == targetValue);

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing a
	 *       <code>null</code> list.
	 */
	@Test
	public void testEvaluateNull() throws RPNException {

		List<String> tokens = null;
		double value = Double.NaN;
		RPNModel<String> model = new RPNStackModel();
		value = evaluator.evaluate(tokens, model);
		assertTrue(Double.isNaN(value));

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing an empty
	 *       list.
	 */
	@Test
	public void testEvaluateEmpty() throws RPNException {

		List<String> tokens = new ArrayList<String>();
		double value = Double.NaN;
		RPNModel<String> model = new RPNStackModel();
		value = evaluator.evaluate(tokens, model);
		assertTrue(Double.isNaN(value));

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing a list with
	 *       not enough arguments.
	 */
	@Test
	public void testEvaluateNotEnoughArgs() throws RPNException {

		List<String> tokens = new ArrayList<String>();
		double value = Double.NaN;
		boolean exceptionCaught = false;
		ErrorCode errorCode = ErrorCode.UNKNOWN;

		tokens.add("100");
		tokens.add("+");

		try {
			RPNModel<String> model = new RPNStackModel();
			value = evaluator.evaluate(tokens, model);
		} catch (RPNException e) {
			exceptionCaught = true;
			errorCode = e.getErrorCode();
		}
		assertTrue(exceptionCaught);
		assertTrue(errorCode.isTooFewArguments());
		assertTrue(Double.isNaN(value));

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing a list with
	 *       too many arguments.
	 */
//	@Test
//	public void testEvaluateTooManyArgs() throws RPNException {
//
//		List<String> tokens = new ArrayList<String>();
//		double value = Double.NaN;
//		boolean exceptionCaught = false;
//		ErrorCode errorCode = ErrorCode.UNKNOWN;
//
//		tokens.add("100");
//		tokens.add("200");
//		tokens.add("300");
//		tokens.add("+");
//		try {
//			RPNModel<String> model = new RPNStackModel();
//			value = evaluator.evaluate(tokens, model);
//		} catch (RPNException e) {
//			exceptionCaught = true;
//			errorCode = e.getErrorCode();
//		}
//		assertTrue(exceptionCaught);
//		assertTrue(errorCode.isTooManyArguments());
//		assertTrue(Double.isNaN(value));
//	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing a simple
	 *       expression and returning the correct value.
	 */
	@Test
	public void testEvaluateSimpleExpression() throws RPNException {

		List<String> tokens = new ArrayList<String>();
		double value = Double.NaN;
		boolean exceptionCaught = false;
		double result = 300;

		tokens.add("100");
		tokens.add("200");
		tokens.add("+");

		try {
			RPNModel<String> model = new RPNStackModel();
			value = evaluator.evaluate(tokens, model);
		} catch (Exception e) {
			exceptionCaught = true;
		}
		assertFalse(exceptionCaught);
		assertTrue(value == result);
	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing a simple
	 *       expression in which it fails.
	 */
	@Test
	public void testEvaluateSimpleExpressionFail() throws RPNException {

		List<String> tokens = new ArrayList<String>();
		double value = Double.NaN;
		boolean exceptionCaught = false;

		// The tokens are in the incorrect order; should throw a "too few
		// arguments' exception.
		tokens.add("100");
		tokens.add("+");
		tokens.add("200");

		try {
			RPNModel<String> model = new RPNStackModel();
			value = evaluator.evaluate(tokens, model);
		} catch (Exception e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
		assertTrue(Double.isNaN(value));

	}

	/**
	 * Test method for {
	 * 
	 * @link DefaultRPNEvaluator#evaluate(java.util.List) } passing in a more
	 *       complex expression.
	 */
	@Test
	public void testEvaluateComplexExpression() throws RPNException {

		List<String> tokens = new ArrayList<String>();
		double value = Double.NaN;
		double x = 100;
		double y = 200;
		double z = 2;
		boolean exceptionCaught = false;
		double result = (x + y) * z;

		tokens.add(Double.toString(x));
		tokens.add(Double.toString(y));
		tokens.add(BasicOperation.PLUS.toString());
		tokens.add(Double.toString(z));
		tokens.add(BasicOperation.TIMES.toString());

		try {
			RPNModel<String> model = new RPNStackModel();
			value = evaluator.evaluate(tokens, model);
		} catch (Exception e) {
			exceptionCaught = true;
		}
		assertFalse(exceptionCaught);
		assertTrue(value == result);
	}
}