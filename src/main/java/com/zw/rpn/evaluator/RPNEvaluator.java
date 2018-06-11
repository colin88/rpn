package com.zw.rpn.evaluator;

import java.util.List;

import com.zw.rpn.RPNModel;
import com.zw.rpn.exception.RPNException;

/**
 * This interface specifies an evaluator that takes a model and returns the
 * value by applying the operations on the values embedded within the model.
 */
public interface RPNEvaluator<E> {

	/**
	 * @param tokens
	 *            The tokens to be evaluated. Must be non-<code>null</code>.
	 *        model
	 *            The stack model for recycle caculate
	 * @return The value obtained by applying the operations to the values found
	 *         within the <code>tokens</code>.
	 * @throws RPNException
	 *             On any error encountered when evaluating the model.
	 */
	public double evaluate(List<E> tokens, RPNModel<String> model) throws RPNException;

}