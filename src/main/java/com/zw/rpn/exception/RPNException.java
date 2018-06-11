package com.zw.rpn.exception;

import java.util.EmptyStackException;

/**
 * This class provides a general-purpose wrapper for any exceptions encountered
 * while parsing or otherwise processing RPN calculations.<br>
 * 
 * An {
 * 
 * @link ErrorCode } enumeration is used for identifying specific types of
 *       exceptions. This is useful for correlating an exception encountered in
 *       the field (or during tests) to a cause, albeit one that doesn't scale
 *       well for cases where there are a large number of these known errors. In
 *       such a case a more robust solution (i.e. one backed by a persistence
 *       mechanism like a database, for example) should be used. <br>
 * 
 *       An embedded {
 * @link Factory } is used for creating new instances. For this case there are
 *       few that need to be supported, but again, this does not scale well for
 *       cases in which there are a large number of errors.<br>
 * 
 *       The factory can be accessed via the {
 * @link #factory } variable. For example:<br>
 * 
 *       <pre>
 *       throw RPNException.factory.createTooFewArgsException();
 *       </pre>
 */
public class RPNException extends Exception {

	private static final long serialVersionUID = 6176803268461593292L;

	/**
	 * Embedded factory that can be used for creating specific exceptions.
	 */
	public static final Factory factory = new RPNException.Factory();

	/**
	 * An {
	 * 
	 * @link ErrorCode } associated with this exception.
	 */
	private ErrorCode errorCode = ErrorCode.UNKNOWN;

	/**
	 * Creates a new instance using the given message.
	 * 
	 * @param message
	 *            The detail message for the exception.
	 */
	protected RPNException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance using the given specific cause.
	 * 
	 * @param cause
	 *            The cause of the exception.
	 */
	protected RPNException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance using the given message and cause.
	 * 
	 * @param message
	 *            The detail message for the exception.
	 * @param cause
	 *            The cause of the exception.
	 */
	protected RPNException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	@Override
	@SuppressWarnings("nls")
	// No need for i18n checks in this method
	public String toString() {

		StringBuilder sb = new StringBuilder("RPNException:\n");
		sb.append(String.format("message: %s\n", getMessage()));

		Throwable cause = getCause();
		if (cause != null) {
			sb.append(String.format("cause: %s\n", cause.toString()));
		}
		return (sb.toString());
	}

	/**
	 * @return The current value of {
	 * @link #errorCode }.
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            The new value for {
	 * @link #errorCode }.
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Convenience method for converting an exception to a human-readable
	 * display string.
	 * 
	 * @param exception
	 *            The exception to be converted into a display {
	 * @link String }. Must be non-<code>null</code>.
	 * @return A {
	 * @link String } representation of the given exception suitable for display
	 *       purposes.
	 */
	// In production this would obtain its strings from a resource bundle.
	@SuppressWarnings("nls") // No i18n support for now.
	public static String toDisplayString(RPNException exception) {

		String message = null;
		if (exception != null) {
			ErrorCode errorCode = exception.getErrorCode();
			switch (errorCode) {
				case EmptyStack:
					message = "Evaluation error: empty stack";
					break;
	
				case EvaluationError:
					message = "Evaluation error";
					break;
	
				case ParsingError:
					message = "Error in parsing";
					break;
	
				case TooFewArguments:
					// message = "Evaluation error: Too few arguments for number of
					// operators";
					message = "insucient parameters";
					break;
	
				case TooManyArguments:
					message = "Evaluation error: Too many arguments";
					break;
	
				default:
					message = "Unknown error";
					break;
			}
		}
		return message;
	}

	/**
	 * This enumeration is used for identifying known types of error conditions
	 * as a way of tagging an exception.
	 */
	@SuppressWarnings("nls")
	// No need for i18n checks in this enum
	public enum ErrorCode {

		UNKNOWN(Integer.MIN_VALUE), TooFewArguments(1001), TooManyArguments(1002), ParsingError(1003), EvaluationError(
				1004), EmptyStack(1005);

		private int errorID;
		
		private ErrorCode(int errorID) {
			this.errorID = errorID;
		}

		public int getErrorID() {
			return errorID;
		}

		/**
		 * {
		 * 
		 * @inheritDoc }
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("ErrorCode (%s):\n", name()));
			sb.append(String.format("errorID: %d", errorID));
			return sb.toString();
		}

		public boolean isUNKNOWN() {
			return this == UNKNOWN;
		}

		public boolean isNotUNKNOWN() {
			return this != UNKNOWN;
		}

		public boolean isTooFewArguments() {
			return this == TooFewArguments;
		}

		public boolean isNotTooFewArguments() {
			return this != TooFewArguments;
		}

		public boolean isTooManyArguments() {
			return this == TooManyArguments;
		}

		public boolean isNotTooManyArguments() {
			return this != TooManyArguments;
		}

		public boolean isParsingError() {
			return this == ParsingError;
		}

		public boolean isNotParsingError() {
			return this != ParsingError;
		}

		public boolean isEvaluationError() {
			return this == EvaluationError;
		}

		public boolean isNotEvaluationError() {
			return this != EvaluationError;
		}

		public boolean isEmptyStack() {
			return this == EmptyStack;
		}

		public boolean isNotEmptyStack() {
			return this != EmptyStack;
		}

	}

	/**
	 * This factory class is used as a convenience for creating known {
	 * 
	 * @link RPNException }s.
	 */
	@SuppressWarnings("nls") // Don't do i18n checks on this factory class.
	public static class Factory {

		/**
		 * Creates an exception relating to a parsing error.
		 * 
		 * @param message
		 *            The detailed error message.
		 * @return The newly minted exception.
		 */
		public final RPNException createParsingException(String message, Throwable cause) {
			RPNException exception = new RPNException(message, cause);
			exception.setErrorCode(ErrorCode.ParsingError);
			return exception;
		}

		/**
		 * Creates an exception for the case when there are too few arguments in
		 * the expression.
		 * 
		 * @return The newly minted exception.
		 */
		public final RPNException createTooFewArgsException() {
			RPNException exception = new RPNException("Not enough arguments for operation");
			exception.setErrorCode(ErrorCode.TooFewArguments);
			return exception;
		}

		/**
		 * Creates an exception for the case when there are too many arguments
		 * in the expression.
		 * 
		 * @return The newly minted exception.
		 */
		public final RPNException createTooManyArgsException() {
			RPNException exception = new RPNException("Too many arguments for operation");
			exception.setErrorCode(ErrorCode.TooManyArguments);
			return exception;
		}

		/**
		 * Creates an exception relating to an error during expression
		 * evaluation.
		 * 
		 * @return The newly minted exception.
		 */
		public final RPNException createEvaluationErrorException() {
			RPNException exception = new RPNException("Error evaluating expression");
			exception.setErrorCode(ErrorCode.EvaluationError);
			return exception;
		}

		/**
		 * For creating an exception relating to the stack being empty when
		 * attempting to remove an item from it.
		 * 
		 * @param cause
		 *            The {
		 * @link EmptyStackException } that is the root cause of this error.
		 * @return The newly minted exception.
		 */
		public final RPNException createEmptyStackException(EmptyStackException cause) {
			RPNException exception = new RPNException(cause);
			exception.setErrorCode(ErrorCode.EmptyStack);
			return exception;
		}

	}

}