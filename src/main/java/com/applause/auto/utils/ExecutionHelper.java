package com.applause.auto.utils;

import com.applause.auto.core.RunningConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecutionHelper {

  protected static final Logger logger = LogManager.getLogger(ExecutionHelper.class);
  private static final String RUNNING_CONFIGURATION = "runningConfiguration";

  public static RunningConfiguration getRunningConfiguration() {
    return RunningConfiguration.valueOf(System.getProperty(RUNNING_CONFIGURATION));
  }
}
