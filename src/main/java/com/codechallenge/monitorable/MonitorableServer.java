package com.codechallenge.monitorable;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

public interface MonitorableServer {

  URI getServerUrl();
  Consumer<HttpResponse<String>> getResponseHandler();
  Consumer<CompletionException> getExceptionHandler();

}
