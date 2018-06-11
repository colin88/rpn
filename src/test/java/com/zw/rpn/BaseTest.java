package com.zw.rpn;
 
import com.zw.rpn.util.LogUtil;
 
/**
 * This class serves as a base for RPN calculator unit tests. In this case it merely serves as a means of ensuring the
 * log4j logging has been properly initialized before the tests are run.
 */
public class BaseTest {
 
    /**
     * Creates a new instance; initializes logging.
     */
    public BaseTest() { 
        LogUtil.initLogging();     
    }
 
}