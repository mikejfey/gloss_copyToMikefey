package com.applause.auto.utils;

import com.applause.auto.framework.SdkHelper;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static com.applause.auto.framework.SdkHelper.getDriver;

public class AllureUtils {
    private static final Logger logger = LogManager.getLogger(AllureUtils.class);

    public static void step(String stepAction){
        Allure.step(stepAction);
        logger.info(stepAction);
    }

    public static void attachScreenshotOnFailure() {
        try {
            if (Objects.nonNull(getDriver())) {
                logger.info("Taking screenshot on test failure");
                try {
                    Allure.addAttachment(
                            "Screenshot on test failure",
                            "image/jpeg",
                            new ByteArrayInputStream(
                                    ((TakesScreenshot) SdkHelper.getDriver()).getScreenshotAs(OutputType.BYTES)),
                            "jpeg");
                } catch (Exception e) {
                    logger.error("Error taking screenshot on test failure: " + e.getMessage());
                }
            }
        } catch (Throwable th) {
            logger.error("Unable to get Webdriver instance");
        }
    }
}
