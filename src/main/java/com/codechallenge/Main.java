package com.codechallenge;

import com.codechallenge.config.ApplicationConfiguration;
import com.codechallenge.monitorable.MagnificentServer;
import com.codechallenge.monitorable.MonitorableServer;
import com.codechallenge.service.ServerMonitoringService;
import com.codechallenge.timer.MonitoringTimer;

public class Main {

  private static final ApplicationConfiguration APP_CONFIGURATION = new ApplicationConfiguration();

  public static void main(String[] args) {
    MonitorableServer magnificentServer = new MagnificentServer();
    ServerMonitoringService monitoringService = new ServerMonitoringService();
    MonitoringTimer timer = new MonitoringTimer(magnificentServer, monitoringService,
        APP_CONFIGURATION.getMonitoringFixedRate());
    timer.start();
  }

}
