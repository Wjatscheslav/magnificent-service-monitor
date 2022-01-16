package com.codechallenge.service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codechallenge.monitorable.MonitorableServer;

public class ServerMonitoringService {

  private static final Logger LOGGER = LogManager.getLogger(ServerMonitoringService.class);
  private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
      .build();

  public void monitorServer(MonitorableServer monitorableServer) {
    try {
      HttpRequest request = buildRequest(monitorableServer.getServerUrl());
      HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenAccept(monitorableServer.getResponseHandler())
        .orTimeout(5, SECONDS)
        .join();
    } catch (CompletionException ex) {
      LOGGER.error(ex.getMessage());
      monitorableServer.getExceptionHandler().accept(ex);
    }
  }

  private HttpRequest buildRequest(URI uri) {
    return HttpRequest.newBuilder()
        .uri(uri)
        .build();
  }

}
