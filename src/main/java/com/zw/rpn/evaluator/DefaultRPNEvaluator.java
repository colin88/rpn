package com.zw.rpn.evaluator;

import java.util.List;

import org.apache.log4j.Logger;

import com.zw.rpn.RPNModel;
import com.zw.rpn.RPNStackModel;
import com.zw.rpn.exception.RPNException;
import com.zw.rpn.operation.ArithmeticOperation;
import com.zw.rpn.operation.BasicOperation;
import com.zw.rpn.operation.BinaryOperation;

/**
 * This class provides a default implementation of an RPN evaluator.
 */
@SuppressWarnings("nls")
// No need for i18n checks in this class
public class DefaultRPNEvaluator implements RPNEvaluator<String> {

	/**
	 * Add this to <code>log4j.properties</code> for enabling output to console.
	 * log4j.logger.com.kermel.rpncalc.evaluator.DefaultRPNEvaluator=DEBUG
	 */
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(DefaultRPNEvaluator.class);

	public double evaluate(List<String> tokens, RPNModel<String> model) throws RPNException {

		double value = Double.NaN;
		
		int OpeSize = 0;
		//int valSize = 0;
		// Skip the case where tokens is null or empty
		if (tokens != null && !tokens.isEmpty()) {
			int numTokens = tokens.size();
			for (int i = 0; i < numTokens; i++) {
				// Get the next token
				String token = tokens.get(i);
				/*if (LOG.isDebugEnabled())
					LOG.debug(String.format("stack: %s  index: %d", token, i));*/

				// Push it onto the stack if it's a value
				if (isValue(token)) {
					model.add(token);
					//valSize++;
				} else {
					OpeSize++;
					// Not a value so it must be an operator
					ArithmeticOperation operation = determineOperation(token);
					if (operation == BasicOperation.UNKNOWN && !"clear".equalsIgnoreCase(token)) {
						throw RPNException.factory.createEvaluationErrorException();
					} else if ("clear".equalsIgnoreCase(token)) {
						while (!model.isEmpty()) {
							model.remove();
						}
					} else {
						// Ensure we have enough arguments on the stack
						if (model.size() < operation.numArgs() && !"sqrt".equalsIgnoreCase(token)) {
							throw RPNException.factory.createTooFewArgsException();
						}

						if (operation instanceof BinaryOperation) {
							// Evaluate the binary operation, pulling the two values from the model
							value = evaluateBinaryOperation((BinaryOperation) operation, model);
							// Push the result onto the stack
							model.add(Double.toString(value));
						}
					}
				}				
			}

			// If there is one item remaining on the stack, that is the resultant value of all operations.
//			if (model.size() == 1 && OpeSize > 0) {
//				value = convertToValue(model.remove());			
//			} 
//			else if (valSize > 0 && valSize-OpeSize > 1) {				
//				throw RPNException.factory.createTooManyArgsException();
//			} else if (valSize > 0 && valSize-OpeSize < 1) {				
//				throw RPNException.factory.createTooFewArgsException();
//			}			
			if (model.size() > 1 && OpeSize > 0) {
				//value = convertToValue(model.remove()); model.remove method will cause lack last result
//				LOG.debug(String.format("   stack : \"%s\" ", model));
			} else if (model.isEmpty()) {
				//throw RPNException.factory.createEvaluationErrorException();
			}
			 
		}
		return value;

	}

	private double evaluateBinaryOperation(BinaryOperation binaryOp, RPNModel<String> model) throws RPNException {

		double value = Double.NaN;
		// Pop the top two values from the stack
		double y = convertToValue(model.remove());
		double x = 0f;
		if (!"SQRT".equalsIgnoreCase(binaryOp.getName())) {
			x = convertToValue(model.remove());
		}
		// Evaluate the operator, using the two values
		value = binaryOp.evaluate(x, y);
		return value;
	}

	/**
	 * Used for determining if the given token is a value.
	 * 
	 * @param token
	 * The token to be checked. Must be non-<code>null</code>.
	 * @return <code>true</code> if the given token is a value;
	 *         <code>false</code> otherwise.
	 */
	// TODO: May want to move this to a base class if/when another implementation is added.
	public boolean isValue(String token) {
		boolean result = false;

		// TODO: Cheating, for now, by relying on Double.parseDouble(), using caught exception as the check.
		// Not very efficient, to be replaced.
		if (token != null) {
			try {
				Double.parseDouble(token);
				result = true;
			} catch (NumberFormatException e) {
				// No need to report this
				result = false;
			}
		}

		/*if (LOG.isDebugEnabled()) {
			LOG.debug(String.format(" item is value : %s  result:%s", token, result));
		}*/

		return result;

	}

	/**
	 * Used for converting a token into a { 
	 * @link BinaryOperation }.
	 * @param token
	 * The token to be converted. Must be non-<code>null</code>.
	 * @return The {
	 * @link BinaryOperation } corresponding to the given token. Guaranteed to
	 *       be non-null, but may be an unknown operation {
	 * @value BasicOperation#UNKNOWN }.
	 */
	public ArithmeticOperation determineOperation(String token) {

		BinaryOperation operation = BasicOperation.UNKNOWN;

		if (token != null) {
			try {
				operation = BasicOperation.valueOfBySymbol(token);
			} catch (Exception e) {
				// In this case we're keeping the exception silent, because we
				// simply use the default of UNKNOWN.
				// Log it though.
				LOG.error(String.format("Error: %s", e.toString()));
			}
		}

//		if (LOG.isDebugEnabled()) {
//			LOG.debug(String.format("    Operation: %s operation: %s", token, operation.getName()));
//		}
		return operation;

	}

	/**
	 * Safety method for converting a {
	 * 
	 * @link String } token into a {
	 * @link Double } value.
	 * 
	 * @param token
	 * The token to be converted. Must be non-<code>null</code>.
	 * @return The value of the given token.
	 * @throws RPNException
	 * On any errors encountered when converting the token to a value.
	 */
	// TODO: May want to move this to a base class if/when another implementation is added.
	public double convertToValue(String token) throws RPNException {

		double value = Double.NaN;
		// Don't convert if the given token is null or empty.
		if (token != null && token.length() > 0) {
			try {
				value = Double.parseDouble(token);
			} catch (NumberFormatException e) {
				// In practice this should never be encountered because
				// evaluate() uses isValue() to ensure the token is
				// a numeric value; however, I'm using this as a safety net to
				// catch any changes to the code made in the
				// future that might alter this assumption.
				// Note that this IS actually encountered during the unit test.
				LOG.error(String.format("Error: %s", e.toString()));
				throw RPNException.factory.createParsingException(String.format("Error parsing token %s", token), e);
			}
		}
		/*if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("token: %s\nvalue: %f", token, value));
		}*/
		return value;

	}

}