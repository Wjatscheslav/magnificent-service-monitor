package com.codechallenge.monitorable;

import static java.lang.String.format;

import java.net.http.HttpResponse;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codechallenge.configuration.ServerConfiguration;

public class MagnificentServer implements MonitorableServer {

  private static final Logger MAGNIFICENT_LOGGER = LogManager.getLogger("MagnificentMonitor");
  private static final String RESPONSE_LOG_FORMAT = "Received: {url: %s, status: %s, response: %s}";
  private static final String EXCEPTION_LOG = "Unable to get response from server: {url: %s, message: %s}";

  private final ServerConfiguration configuration;

  public MagnificentServer(ServerConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public ServerConfiguration getServerConfiguration() {
    return configuration;
  }

  @Override
  public Consumer<HttpResponse<String>> getResponseHandler() {
    return response -> {
      if (isSuccessful(response.statusCode())) {
        MAGNIFICENT_LOGGER.info(format(RESPONSE_LOG_FORMAT, configuration.getServerUrl(), response.statusCode(),
            response.body()));
      } else {
        MAGNIFICENT_LOGGER.warn(format(RESPONSE_LOG_FORMAT, configuration.getServerUrl(), response.statusCode(),
            response.body()));
      }
    };
  }

  @Override
  public Consumer<Exception> getExceptionHandler() {
    return ex -> MAGNIFICENT_LOGGER.error(format(EXCEPTION_LOG, configuration.getServerUrl(), ex.getMessage()));
  }

  private boolean isSuccessful(int statusCode) {
    return statusCode >= 200 && statusCode <= 299;
  }
}
