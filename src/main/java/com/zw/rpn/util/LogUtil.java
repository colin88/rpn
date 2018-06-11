package com.zw.rpn.util;

import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
	private LogUtil() {
		
	}
	
	public static final void initLogging() {
		PropertyConfigurator.configure("config/log4j.properties");
	}
}
