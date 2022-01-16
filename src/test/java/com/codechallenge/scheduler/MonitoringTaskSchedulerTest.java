package com.codechallenge.scheduler;

import static com.codechallenge.utils.TestUtils.clearLogFile;
import static com.codechallenge.utils.TestUtils.readStringsFromLogFile;
import static com.codechallenge.utils.TestUtils.removeLogFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;

import com.codechallenge.configuration.ServerConfiguration;
import com.codechallenge.monitorable.MagnificentServer;
import com.codechallenge.monitorable.MonitorableServer;
import com.codechallenge.service.ServerMonitoringService;

@ExtendWith(MockServerExtension.class)
public class MonitoringTaskSchedulerTest {

  private static final ServerConfiguration TEST_CONFIG = new ServerConfiguration("test-server.properties");

  private final MonitoringTaskScheduler scheduler = initScheduler();
  private ClientAndServer mockServer;

  @BeforeEach
  public void beforeEach() {
    mockServer = startClientAndServer(1234);
  }

  @AfterEach
  public void afterEach() throws FileNotFoundException {
    clearLogFile();
    if (mockServer.isRunning()) {
      mockServer.stop();
    }
  }

  @AfterAll
  public static void afterAll() throws IOException {
    removeLogFile();
  }

  @Test
  public void shouldAddInfoLogMessageForSuccessRequest() throws InterruptedException, IOException {
    // given
    mockServer
        .when(request()
            .withPath("/")
            .withMethod("GET"))
        .respond(response()
            .withStatusCode(200));

    // when
    ScheduledFuture<?> future = scheduler.start();
    Thread.sleep(TEST_CONFIG.getMonitoringConnectionTimeout());
    future.cancel(true);

    // then
    List<String> actual = readStringsFromLogFile();
    assertEquals(1, actual.size());
    assertTrue(actual.get(0).contains("INFO"));
    assertTrue(actual.get(0).contains("status: 200"));
  }

  @Test
  public void shouldAddWarnLogMessageForFailedRequest() throws InterruptedException, IOException {
    // given
    mockServer
        .when(request()
            .withPath("/")
            .withMethod("GET"))
        .respond(response()
            .withStatusCode(500));

    // when
    ScheduledFuture<?> future = scheduler.start();
    Thread.sleep(TEST_CONFIG.getMonitoringConnectionTimeout());
    future.cancel(true);

    // then
    List<String> actual = readStringsFromLogFile();
    assertEquals(1, actual.size());
    assertTrue(actual.get(0).contains("WARN"));
    assertTrue(actual.get(0).contains("status: 500"));
  }

  @Test
  public void shouldAddErrorLogMessageIfServerNotAvailable() throws InterruptedException, IOException {
    // given
    mockServer.stop();

    // when
    ScheduledFuture<?> future = scheduler.start();
    Thread.sleep(TEST_CONFIG.getMonitoringConnectionTimeout());
    future.cancel(true);

    // then
    List<String> actual = readStringsFromLogFile();
    assertEquals(1, actual.size());
    assertTrue(actual.get(0).contains("ERROR"));
  }

  private MonitoringTaskScheduler initScheduler() {
    MonitorableServer magnificentServer = new MagnificentServer(TEST_CONFIG);
    ServerMonitoringService monitoringService = new ServerMonitoringService();
    return new MonitoringTaskScheduler(magnificentServer, monitoringService);
  }

}
