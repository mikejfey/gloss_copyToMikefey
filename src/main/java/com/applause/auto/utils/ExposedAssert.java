package com.applause.auto.utils;

import org.testng.Assert;

import static com.applause.auto.utils.AllureUtils.step;

public class ExposedAssert {

    public static void assertTrue(String stepToLog, boolean condition, String onFailMessage){
        step(stepToLog);
        Assert.assertTrue(condition, onFailMessage);
    }

    public static void assertFalse(String stepToLog, boolean condition, String onFailMessage){
        step(stepToLog);
        Assert.assertFalse(condition, onFailMessage);
    }

    public static void assertEquals(String stepToLog, Object actual, Object expected, String onFailMessage){
        step(stepToLog);
        Assert.assertEquals(actual, expected, onFailMessage);
    }

    public static void assertNotEquals(String stepToLog, Object actual, Object expected, String onFailMessage){
        step(stepToLog);
        Assert.assertNotEquals(actual, expected, onFailMessage);
    }
}
