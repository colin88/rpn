package com.zw.rpn.parser;

import java.util.List;

/**
 * This interface defines an entity that is responsible for parsing and
 * evaluating a line of RPN input.
 */
public interface RPNParser<E> {

	/**
	 * @param line
	 *            The line of text to be parsed. Must be non-<code>null</code>.
	 * @return An {
	 * @link RPNModel } containing the tokens that were parsed from the
	 *       <code>line</code> of text.
	 */
	public List<E> parse(String line);

}