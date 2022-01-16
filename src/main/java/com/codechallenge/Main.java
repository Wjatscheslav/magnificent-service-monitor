package com.codechallenge;

import com.codechallenge.configuration.ServerConfiguration;
import com.codechallenge.monitorable.MagnificentServer;
import com.codechallenge.monitorable.MonitorableServer;
import com.codechallenge.service.ServerMonitoringService;
import com.codechallenge.scheduler.MonitoringTaskScheduler;

public class Main {

  private static final String MAGNIFICENT_SERVER_PROPS = "magnificent.properties";
  private static final ServerConfiguration SERVER_CONFIG = new ServerConfiguration(MAGNIFICENT_SERVER_PROPS);

  public static void main(String[] args) {
    MonitorableServer magnificentServer = new MagnificentServer(SERVER_CONFIG);
    ServerMonitoringService monitoringService = new ServerMonitoringService();
    MonitoringTaskScheduler scheduler = new MonitoringTaskScheduler(magnificentServer, monitoringService);
    scheduler.start();
  }

}
