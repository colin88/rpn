package com.zw.rpn.calculator;

import java.util.List;

import org.apache.log4j.Logger;

import com.zw.rpn.RPNModel;
import com.zw.rpn.RPNStackModel;
import com.zw.rpn.evaluator.DefaultRPNEvaluator;
import com.zw.rpn.evaluator.RPNEvaluator;
import com.zw.rpn.exception.RPNException;
import com.zw.rpn.parser.DefaultRPNParser;
import com.zw.rpn.parser.RPNParser;

/**
 * This class provides a default one-stop means for parsing and evaluating a
 * line of input. Merely a convenience so you don't have to instantiate your own
 * {
 * 
 * @link RPNParser } and {
 * @link RPNEvaluator }.<br>
 * 
 * Use the <code>static</code> factory method {
 * @link #newInstance() } to obtain an instance of this class.
 */
public class DefaultRPNCalculator implements RPNCalculator<String> {

	/**
	 * Add this to <code>log4j.properties</code> for enabling output to console.
	 * log4j.logger.com.zw.rpn.DefaultRPNCalculator=DEBUG
	 */
	private static Logger LOG = Logger.getLogger(DefaultRPNCalculator.class);
	private RPNParser<String> parser;
	private RPNEvaluator<String> evaluator;

	/**
	 * Creates a new instance
	 */
	private DefaultRPNCalculator() {
		parser = new DefaultRPNParser();
		evaluator = new DefaultRPNEvaluator();
	}

	/**
	 * @return A new {
	 * @link DefaultRPNCalculator } instance.
	 */
	public static final DefaultRPNCalculator newInstance() {
		return new DefaultRPNCalculator();
	}

	/**
	 * { 
	 * @inheritDoc }
	 */
	@SuppressWarnings("nls") // For the purposes of this exercise we don't care about i18n
	public double parseAndEvaluate(String line, RPNModel<String> model) throws RPNException {
		List<String> tokens = parser.parse(line);
		
		double value = evaluator.evaluate(tokens, model);
		if (LOG.isDebugEnabled() && String.valueOf(value) != String.valueOf(Double.NaN))
			LOG.debug(String.format("stack: %f", value));
		return value;
	}

}