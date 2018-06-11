package com.zw.rpn.operation;
 
/**
 * This interface defines the responsibilities of a binary arithmetic operation. 
 */
public interface BinaryOperation extends ArithmeticOperation {
 
    /**
     * Used for applying a binary operation to two values.
     * 
     * @param x
     *            The first number to be evaluated.
     * @param y
     *            The other number to be evaluated.
     * @return The result of this operation.
     */
    public double evaluate(double x, double y);
 
}