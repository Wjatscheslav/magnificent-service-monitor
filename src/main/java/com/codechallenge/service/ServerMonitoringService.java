package com.codechallenge.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codechallenge.configuration.ServerConfiguration;
import com.codechallenge.monitorable.MonitorableServer;

public class ServerMonitoringService {

  private static final Logger LOGGER = LogManager.getLogger(ServerMonitoringService.class);
  private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
      .build();

  public void monitorServer(MonitorableServer monitorable) {
    try {
      HttpRequest request = buildRequest(monitorable.getServerConfiguration());
      HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
      monitorable.getResponseHandler().accept(response);
    } catch (InterruptedException | IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
      monitorable.getExceptionHandler().accept(ex);
    }
  }

  private HttpRequest buildRequest(ServerConfiguration configuration) {
    return HttpRequest.newBuilder()
        .uri(configuration.getServerUrl())
        .timeout(Duration.ofMillis(configuration.getMonitoringConnectionTimeout()))
        .build();
  }

}
