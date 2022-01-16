package com.codechallenge.monitorable;

import java.net.http.HttpResponse;
import java.util.function.Consumer;

import com.codechallenge.configuration.ServerConfiguration;

public interface MonitorableServer {

  Consumer<HttpResponse<String>> getResponseHandler();
  Consumer<Exception> getExceptionHandler();
  ServerConfiguration getServerConfiguration();

}
