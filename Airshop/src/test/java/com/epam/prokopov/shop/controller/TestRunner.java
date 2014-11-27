package com.epam.prokopov.shop.controller;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
        
public class TestRunner {	
        
    public TestResult run(final Class<?> testClass, final String methodName) {
        try {	
            BlockJUnit4ClassRunner runner = new BlockJUnit4ClassRunner(testClass) {
                @Override	
                protected List<FrameworkMethod> computeTestMethods() {	
                    try {	
                        Method method = testClass.getMethod(methodName);	
                        return Arrays.asList(new FrameworkMethod(method));	
                    } catch (NoSuchMethodException e) {	
                        throw new RuntimeException(e);	
                    }	
                }	
            };
            TestResult result = new TestResult();
            runner.run(result);
            return result;
        } catch (InitializationError e) {	
            throw new RuntimeException(e);	
        }	
    }	
            
}