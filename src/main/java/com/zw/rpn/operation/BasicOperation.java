package com.zw.rpn.operation;

/**
 * This <code>enum</code> provides implementations for basic binary arithmetic
 * operations.
 */
@SuppressWarnings("nls")
// No need for i18n checks here.
public enum BasicOperation implements BinaryOperation {

	UNKNOWN("unknown") {
		/**
		 * {
		 * 
		 * @inheritDoc }
		 */
		public double evaluate(double x, double y) {
			return Double.NaN;
		}
	},

	PLUS("+") {
		public double evaluate(double x, double y) {
			return x + y;
		}
	},

	MINUS("-") {
		public double evaluate(double x, double y) {
			return x - y;
		}
	},

	TIMES("*") {
		public double evaluate(double x, double y) {
			return x * y;
		}
	},

	DIVIDE("/") {
		public double evaluate(double x, double y) {
			return x / y;
		}
	},
	
	SQRT("sqrt") {
		public double evaluate(double x, double y) {			
			return Math.sqrt(y);
		}
	};

	/**
	 * The symbol representing this operation.
	 */
	private final String symbol;

	private BasicOperation(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Used for converting from a symbol to an operation (i.e. "+" converts to {
	 * 
	 * @link #PLUS }).
	 * 
	 * @param symbol
	 *            The operation symbol to convert to. Must be
	 *            non-<code>null</code>.
	 * @return The operation for the given symbol. Guaranteed to be
	 *         non-<code>null</code> but may be {
	 * @link #UNKNOWN }.
	 */
	public static final BasicOperation valueOfBySymbol(String symbol) {

		BasicOperation operation = BasicOperation.UNKNOWN;
		if (symbol != null) {
			BasicOperation[] ops = BasicOperation.values();
			for (BasicOperation op : ops) {
				if (symbol.equals(op.symbol)) {
					operation = op;
					break;
				}
			}
		}
		return operation;
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	public int numArgs() {
		return 2;
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	public String getName() {
		return name();
	}

	/**
	 * {
	 * 
	 * @inheritDoc }
	 */
	@Override
	public String toString() {
		return symbol;
	}

	/**
	 * Convenience method for determining if this operation is {
	 * 
	 * @link #UNKNOWN }.
	 * 
	 * @return <code>true</code> if this operation is {
	 * @link #UNKNOWN }.
	 */
	public boolean isUNKNOWN() {
		return this == UNKNOWN;
	}

	/**
	 * Convenience method for determining if this operation is anything but {
	 * 
	 * @link #UNKNOWN }.
	 * 
	 * @return <code>true</code> if this operation is not {
	 * @link #UNKNOWN }.
	 */
	public boolean isNotUNKNOWN() {
		return this != UNKNOWN;
	}

}