
package com.zw.rpn.operation;

/**
 * This interface specifies the basis arithmetic operation used in the RPN caculator. 
 *
 */
public interface ArithmeticOperation {
	
	/**
	 * @return The number of arguments expected by this operation's {
	 * @link #evaluate(double, double)
	 * } method.
	 */
	public int numArgs();
	
	/**
	 * @return The name of this operation
	 */
	public String getName();
}
