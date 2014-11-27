package com.epam.prokopov.shop.controller;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
        
public class AcceptanceTest {	
        
    private static TestRunner runner;	
            
    @BeforeClass	
    public static void setUp() {	
        runner = new TestRunner();	
    }	
            
    @Test	
    public void testTask_11_1_WithoutZipping() {
        TestResult result = runner.run(GZipFilterTest.class, "noZippingTest");
        assertTrue(result.wasSuccessful());
    }	
            
    @Test	
    public void testTask_11_1_WithZipping() {
        TestResult result = runner.run(GZipFilterTest.class, "zippingTest");
        assertTrue(result.wasSuccessful());
    }	
            
    @Test	
    public void testTask_11_2() {
        TestResult result = runner.run(GenerationTimeFilterTest.class, "timeFilterTest");
        assertTrue(result.wasSuccessful());
    }	
            
    @Test	
    public void testTask_11_3() {
        TestResult result = runner.run(EncodingFilterTest.class, "encodingFilterTest");
        assertTrue(result.wasSuccessful());
    }	

    @Test
    public void testTask_12_CookieLocalizationWithoutRequestedLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "cookieLocalizationWithoutRequestedLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_12_CookieLocalizationWithContainsLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "cookieLocalizationWithContainsLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_12_CookieLocalizationWithUncontainsLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "cookieLocalizationWithUncontainsLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_12_SessionLocalizationWithoutRequestedLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "sessionLocalizationWithoutRequestedLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_12_SessionLocalizationWithContainsLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "sessionLocalizationWithContainsLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_12_SessionLocalizationWithUncontainsLanguage(){
        TestResult result = runner.run(LocationFilterTest.class, "sessionLocalizationWithUncontainsLanguage");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_14_HasRestrictionsTest(){
        TestResult result = runner.run(SecurityManagerTest.class, "hasRestrictionsTest");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_14_HasNoRestrictionsTest(){
        TestResult result = runner.run(SecurityManagerTest.class, "hasNoRestrictionsTest");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_14_CheckHasAcess(){
        TestResult result = runner.run(SecurityManagerTest.class, "checkHasAcess");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_14_CheckHasNoAcess(){
        TestResult result = runner.run(SecurityManagerTest.class, "checkHasNoAcess");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_15_FullChainTest(){
        TestResult result = runner.run(ProductFilterLinkTest.class, "fullChainTest");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_15_EmptyChainTest(){
        TestResult result = runner.run(ProductFilterLinkTest.class, "emptyChainTest");
        assertTrue(result.wasSuccessful());
    }

    @Test
    public void testTask_15_NotFullChainTest(){
        TestResult result = runner.run(ProductFilterLinkTest.class, "notFullChainTest");
        assertTrue(result.wasSuccessful());
    }

}