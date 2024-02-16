package com.applause.auto.utils;

import io.qameta.allure.listener.StepLifecycleListener;
import org.testng.*;

import java.util.Objects;

public class TestListener implements IInvokedMethodListener, StepLifecycleListener, ITestNGListener, ITestListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (Objects.nonNull(testResult.getThrowable())) {
                AllureUtils.attachScreenshotOnFailure();
            }
        }
    }
}
