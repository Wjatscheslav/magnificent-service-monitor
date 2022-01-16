package com.codechallenge.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConfiguration {

  private static final Logger LOGGER = LogManager.getLogger(ServerConfiguration.class);

  private static final String MONITORING_SERVER_URL = "monitoring.server.url";
  private static final String MONITORING_SERVER_PORT = "monitoring.server.port";
  private static final String MONITORING_FIXED_RATE = "monitoring.server.schedule.fixed.rate";
  private static final String MONITORING_TIMEOUT = "monitoring.server.connection.timeout";
  private final Properties properties = new Properties();

  public ServerConfiguration(String filename) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream is = loader.getResourceAsStream(filename);
    try {
      properties.load(is);
    } catch (IOException e) {
      LOGGER.error(String.format("Properties could not be loaded from the file: %s", filename));
      throw new UncheckedIOException(e);
    }
  }

  public URI getServerUrl() {
    return URI.create(getProperty(MONITORING_SERVER_URL) + ":" + getProperty(MONITORING_SERVER_PORT));
  }

  public Long getMonitoringConnectionTimeout() {
    return Long.valueOf(getProperty(MONITORING_TIMEOUT));
  }

  public Long getMonitoringFixedRate() {
    return Long.valueOf(getProperty(MONITORING_FIXED_RATE));
  }

  private String getProperty(String key) {
    return properties.getProperty(key);
  }

}
