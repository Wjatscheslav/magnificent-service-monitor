package com.codechallenge.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationConfiguration {

  private static final Logger LOGGER = LogManager.getLogger(ApplicationConfiguration.class);

  private static final String FILE_NAME = "application.properties";

  private static final String MAGNIFICENT_SERVER_URL = "magnificent.server.url";
  private static final String MAGNIFICENT_SERVER_PORT = "magnificent.server.port";
  private static final String MONITORING_FIXED_RATE = "monitoring.server.schedule.fixed.rate";
  private final Properties properties = new Properties();

  public ApplicationConfiguration() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream is = loader.getResourceAsStream(FILE_NAME);
    try {
      properties.load(is);
    } catch (IOException e) {
      LOGGER.error(String.format("Properties could not be loaded from the file: %s", FILE_NAME));
      throw new UncheckedIOException(e);
    }
  }

  public String getMagnificentServerUrl() {
    return getProperty(MAGNIFICENT_SERVER_URL);
  }

  public String getMagnificentServerPort() {
    return getProperty(MAGNIFICENT_SERVER_PORT);
  }

  public Long getMonitoringFixedRate() {
    return Long.valueOf(getProperty(MONITORING_FIXED_RATE));
  }

  private String getProperty(String key) {
    return properties.getProperty(key);
  }

}
