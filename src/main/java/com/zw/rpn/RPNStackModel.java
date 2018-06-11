package com.zw.rpn;

import java.util.EmptyStackException;
import java.util.Stack;

import com.zw.rpn.exception.RPNException;

/**
 * This class provides a stack-based implementation of an RPN model.
 */
public class RPNStackModel implements RPNModel<String> {

	private Stack<String> stack;

	/**
	 * Creates a new instance
	 */
	public RPNStackModel() {
		stack = new Stack<String>();
	}

	/**
	 * { 
	 * @inheritDoc }
	 */
	public void add(String item) {
		if (item != null) {
			stack.push(item);
		}
	}

	/**
	 * { 
	 * @inheritDoc }
	 */
	public String remove() throws RPNException {
		String value = null;
		try {
			value = stack.pop();
		} catch (EmptyStackException e) {
			throw RPNException.factory.createEmptyStackException(e);
		}
		return value;
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	public int size() {
		return stack.size();
	}

}