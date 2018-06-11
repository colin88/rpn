package com.zw.rpn.calculator;

import com.zw.rpn.RPNModel;
import com.zw.rpn.exception.RPNException;

/**
 * This interface specifies the responsibilities of a one-stop parse and
 * evaluation RPN calculator.
 */
public interface RPNCalculator<E> {

	/**
	 * Performs a parse of the given line, passing the result of the parse into
	 * an evaluator which produces the result.
	 * 
	 * @param line
	 *            The line of text to be parsed and evaluated. Must be
	 *            non-<code>null</code>.
	 * @return The resultant value from evaluating the parsed line of text.
	 * @throws RPNException
	 *             On any errors encountered while parsing or evaluating the
	 *             given line of text.
	 */
	public double parseAndEvaluate(String line, RPNModel<String> model) throws RPNException;

}