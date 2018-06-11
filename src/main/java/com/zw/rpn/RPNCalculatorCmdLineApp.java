package com.zw.rpn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;

import com.zw.rpn.calculator.DefaultRPNCalculator;
import com.zw.rpn.calculator.RPNCalculator;
import com.zw.rpn.exception.RPNException;

/**
 * This class provides a rudimentary command-line application that implements
 * the RPN calculator.
 */
@SuppressWarnings("nls") // Not doing any i18n checks in here
public class RPNCalculatorCmdLineApp {

	public static void main(String[] args) {

		initLogging();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		RPNCalculator<String> calculator = DefaultRPNCalculator.newInstance();
		boolean done = false;
		double value = Double.NaN;
		
		RPNModel<String> model = new RPNStackModel();
		List<String> list = new ArrayList<String>();
		do {
					
			try {				
				String item = null;
				list.removeAll(list);
				StringBuffer printbf = new StringBuffer();
				while (!model.isEmpty()) {
					item = model.remove();
					list.add(item);
				}				
				
				for (int i = list.size() - 1; i >= 0; i--) {
					String it = list.get(i);					
					printbf.append(" " + it);
					model.add(it);
				}
					
				if (model.size() > 0) {
					System.out.print("stack: " + printbf.toString() + "\n");					
				}
				String line = in.readLine();
				value = calculator.parseAndEvaluate(line, model);
				//System.out.println(value);

			} catch (IOException e) {
				e.printStackTrace();
				done = true;
			} catch (RPNException rpe) {
				reportError(rpe);
			}
		} while (done == false);
	}

	private static void initLogging() {
		PropertyConfigurator.configure("config/log4j.properties");
	}

	private static void reportError(RPNException exception) {
		if (exception != null) {
			String message = RPNException.toDisplayString(exception);
			if (message != null) {
				System.out.println(String.format("- %s\n", message));
			}
		}
	}

}