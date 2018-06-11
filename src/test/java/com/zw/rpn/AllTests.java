package com.zw.rpn;
 
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.JUnit4TestAdapter;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
 
    // Arranged alphabetically
    BasicOperationTest.class,
    DefaultRPNEvaluatorTest.class,
    DefaultRPNParserTest.class,
    RPNIntegrationTest.class,
    RPNStackModelTest.class
 
})
 
/**
 * This class provides an all-encompassing test of all the known unit tests for the RPN Calculator classes.
 */
public class AllTests {
 
 
    @SuppressWarnings("nls") // No need for i18n support in the unit tests
    public static junit.framework.Test suite() { 
        return new JUnit4TestAdapter(AllTests.class);     
    }
 
 
}