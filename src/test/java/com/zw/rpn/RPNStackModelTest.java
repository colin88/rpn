package com.zw.rpn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zw.rpn.exception.RPNException;

/**
 * This class provides the unit test for the {
 * 
 * @link RPNStackModel } class.
 */
@SuppressWarnings("nls") // No need for i18n checks here
public class RPNStackModelTest {

	// In reality this would be included in a framework utility class, in the
	// manner Apache Commons' StringUtil does.
	private static final String EMPTY = "";

	// The model used for our tests. Is constructed in setUp() and discarded in
	// tearDown().
	private RPNStackModel model;

	// Constants for colors array below
	private static final String RED = "red";
	private static final String GREEN = "green";
	private static final String BLUE = "blue";

	// Used for testAddRemove() to ensure items are added and removed from stack
	// in the proper order
	private String[] colors = {
			RED, GREEN, BLUE
	};

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		model = new RPNStackModel();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		model = null;
	}

	/**
	 * Test method for {
	 * 
	 * @link RPNStackModel#add(String) }.
	 */
	@Test
	public void testAdd() {
		String item = null;
		String target = "target";

		// Add of null item; should have no effect
		model.add(item);
		assertTrue(model.isEmpty());

		// Add of empty string
		item = EMPTY;
		model.add(item);
		assertFalse(model.isEmpty());

		// Add of non-null, non-empty string
		item = target;
		model.add(item);
		assertFalse(model.isEmpty());
		assertTrue(model.size() == 2);

	}

	/**
	 * Test method for {
	 * 
	 * @link RPNStackModel#remove() }.
	 */
	@Test
	public void testRemove() {

		String target = "target";
		String result = null;
		boolean exceptionCaught = false;

		// Removing an item from an empty stack should throw an exception
		try {
			result = model.remove();
		} catch (RPNException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);

		// Test of removing an item from the stack
		model.add(target);
		exceptionCaught = false;
		try {
			result = model.remove();
		} catch (RPNException e) {
			exceptionCaught = true;
		}
		assertFalse(exceptionCaught);
		assertNotNull(result);
		assertTrue(result.equals(target));

		// Attempt to remove again should throw an exception
		exceptionCaught = false;
		try {
			result = model.remove();
		} catch (RPNException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
	}

	/**
	 * Test method for {
	 * 
	 * @link RPNStackModel#isEmpty() }.
	 */
	@Test
	public void testIsEmpty() {

		String target = "target";
		// By default the stack is empty
		assertTrue(model.isEmpty());
		// Add an item; not it shouldn't be empty
		model.add(target);
		assertFalse(model.isEmpty());
		// Remove the item; stack should now be empty
		try {
			model.remove();
		} catch (RPNException e) {
			// Essentially ignored for this test
			e.printStackTrace();
		}
		assertTrue(model.isEmpty());
	}

	/**
	 * Test method for {
	 * 
	 * @link RPNStackModel#add() } and {
	 * @link RPNStackModel#remove() } used in combination.
	 */
	@Test
	public void testAddRemove() {

		String result = null;
		int numItems = colors.length;

		// Add the colors
		for (int i = 0; i < numItems; i++) {
			model.add(colors[i]);
		}
		assertFalse(model.isEmpty());
		assertTrue(model.size() == numItems);

		// Remove the colors; should be in reverse order
		try {
			result = model.remove();
		} catch (RPNException e) {
			// Essentially ignored for this test
			e.printStackTrace();
		}
		assertNotNull(result);
		assertTrue(result.equals(BLUE));

		try {
			result = model.remove();
		} catch (RPNException e) {
			// Essentially ignored for this test
			e.printStackTrace();
		}
		assertNotNull(result);
		assertTrue(result.equals(GREEN));

		try {
			result = model.remove();
		} catch (RPNException e) {
			// Essentially ignored for this test
			e.printStackTrace();
		}
		assertNotNull(result);
		assertTrue(result.equals(RED));

	}

}