package com.zw.rpn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zw.rpn.operation.BasicOperation;

/**
 * This class provides the unit test for the {
 * 
 * @link BasicOperation } class. These tests are very rudimentary here; they are
 *       intended primarily to ensure the basic functionality of each operator
 *       works as expected, which will come in handy if and when the operators
 *       are extended.
 */
public class BasicOperationTest {

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {
	 * 
	 * @link BasicOperation#PLUS }.
	 */
	@Test
	public void testPlus() {

		double x = 0;
		double y = 0;
		double result = 0;

		result = BasicOperation.PLUS.evaluate(x, y);
		assertTrue(result == 0);

		x = Double.MAX_VALUE;
		result = BasicOperation.PLUS.evaluate(x, y);
		assertTrue(result == Double.MAX_VALUE);

		x = 0;
		y = Double.MIN_VALUE;
		result = BasicOperation.PLUS.evaluate(x, y);
		assertTrue(result == Double.MIN_VALUE);

		x = 1;
		y = -1;
		result = BasicOperation.PLUS.evaluate(x, y);
		assertTrue(result == 0);

	}

	/**
	 * Test method for {
	 * 
	 * @link BasicOperation#MINUS }.
	 */
	@Test
	public void testMinus() {

		double x = 0;
		double y = 0;
		double result = 0;

		result = BasicOperation.MINUS.evaluate(x, y);
		assertTrue(result == 0);

	}

	/**
	 * Test method for {
	 * 
	 * @link BasicOperation#TIMES }.
	 */
	@Test
	public void testTimes() {

		double x = 0;
		double y = 0;
		double result = 0;

		result = BasicOperation.TIMES.evaluate(x, y);
		assertTrue(result == 0);

		x = Double.MAX_VALUE;
		result = BasicOperation.TIMES.evaluate(x, y);
		assertTrue(result == 0);

		x = 0;
		y = Double.MIN_VALUE;
		result = BasicOperation.TIMES.evaluate(x, y);
		assertTrue(result == 0);

		x = 1;
		y = -1;
		result = BasicOperation.TIMES.evaluate(x, y);
		assertTrue(result == -1);

		x = 2;
		y = 3;
		result = BasicOperation.TIMES.evaluate(x, y);
		assertTrue(result == 6);

	}

	/**
	 * Test method for {
	 * 
	 * @link BasicOperation#DIVIDE }.
	 */
	@Test
	public void testDivide() {

		double x = 0;
		double y = 0;
		Double result = null; // Using a Double object here in order to test for
								// NaN value

		// Divide by zero = NaN
		result = BasicOperation.DIVIDE.evaluate(x, y);
		assertTrue(result.isNaN());

		// Divide zero by one = zero
		y = 1;
		result = BasicOperation.DIVIDE.evaluate(x, y);
		assertFalse(result.isNaN());
		assertTrue(result == 0);

		// Divide one by one = one
		x = 1;
		result = BasicOperation.DIVIDE.evaluate(x, y);
		assertTrue(result == 1);

		// Divide 10 by two = five
		x = 10;
		y = 2;
		result = BasicOperation.DIVIDE.evaluate(x, y);
		assertTrue(result == 5);

	}

}